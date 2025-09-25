import module java.base;
import module java.net.http;

void main() {
    var mavenRepo = Optional.ofNullable(System.getenv("MAVEN_REPO")).filter(s -> !s.isBlank()).orElse("https://repo1.maven.org/maven2");
    IO.println("Using Maven repository: " + mavenRepo);
    IO.println("Starting dependency download...");
    try {
        Files.createDirectories(Paths.get("lib"));
        for (var dependency : Files.readAllLines(Paths.get("lib.txt"))) {
            var dep = dependency.trim();
            if (!dep.isEmpty() && !dep.startsWith("#")) {
                processDependency(dep, mavenRepo);
            }
        }
        IO.println("All dependencies downloaded successfully!");
    } catch (IOException e) {
        IO.println("Error: " + e.getMessage());
    }
}

void processDependency(String dependency, String mavenRepo) {
    try {
        IO.println("Processing: " + dependency);
        var parts = parseDependency(dependency);
        downloadIfNeeded(parts, mavenRepo);
    } catch (IllegalArgumentException e) {
        IO.println("  " + e.getMessage());
    } catch (IOException e) {
        IO.println("  Error processing " + dependency + ": " + e.getMessage());
    }
}

String[] parseDependency(String dependency) throws IllegalArgumentException {
    var parts = dependency.split(":");
    if (parts.length != 3) throw new IllegalArgumentException("Invalid dependency format: " + dependency);
    return parts;
}

void downloadIfNeeded(String[] parts, String mavenRepo) throws IOException {
    var jarName = parts[1] + "-" + parts[2] + ".jar";
    var outputPath = Paths.get("lib", jarName);
    if (Files.exists(outputPath)) {
        IO.println("  Already exists: " + jarName);
        return;
    }
    var url = mavenRepo + "/" + parts[0].replace('.', '/') + "/" + parts[1] + "/" + parts[2] + "/" + jarName;
    IO.println("  Downloading from: " + url);
    try {
        var response = sendHttpRequest(url);
        if (response.statusCode() != 200) throw new IOException("Failed to download: HTTP " + response.statusCode());
        Files.copy((InputStream) response.body(), outputPath, StandardCopyOption.REPLACE_EXISTING);
        IO.println("  Downloaded: " + jarName);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IOException("Download interrupted", e);
    }
}

HttpResponse sendHttpRequest(String url) throws IOException, InterruptedException {
    var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).connectTimeout(Duration.ofSeconds(10)).build();
    var request = HttpRequest.newBuilder().uri(URI.create(url)).header("User-Agent", "LibUp/1.0").timeout(Duration.ofSeconds(30)).GET().build();
    return client.send(request, HttpResponse.BodyHandlers.ofInputStream());
}