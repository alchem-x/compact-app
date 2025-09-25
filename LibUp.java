import module java.base;
import module java.net.http;

void main() {
    var libFile = "lib.txt";
    var libDir = "lib";

    // Get Maven repository URL from environment variable, default to Maven Central
    var mavenRepo = System.getenv("MAVEN_REPO");
    if (mavenRepo == null || mavenRepo.isBlank()) {
        mavenRepo = "https://repo1.maven.org/maven2";
    }

    IO.println("Using Maven repository: " + mavenRepo);
    IO.println("Starting dependency download...");

    try {
        // Create lib directory if it doesn't exist
        Files.createDirectories(Paths.get(libDir));

        // Read lib.txt file
        var dependencies = Files.readAllLines(Paths.get(libFile));

        for (var dependency : dependencies) {
            var dep = dependency.trim();
            if (dep.isEmpty() || dep.startsWith("#")) {
                continue;
            }

            IO.println("Processing: " + dep);
            downloadDependency(dep, libDir, mavenRepo);
        }

        IO.println("All dependencies downloaded successfully!");

    } catch (IOException e) {
        IO.println("Error: " + e.getMessage());
    }
}

void downloadDependency(String dependency, String libDir, String mavenRepo) throws IOException {
    // Parse Maven dependency format: groupId:artifactId:version
    var parts = dependency.split(":");
    if (parts.length < 3) {
        IO.println("Invalid dependency format: " + dependency);
        return;
    }

    var groupId = parts[0];
    var artifactId = parts[1];
    var version = parts[2];

    // Convert to Maven Central URL path
    var groupPath = groupId.replace('.', '/');
    var jarName = artifactId + "-" + version + ".jar";
    var url = mavenRepo + "/" + groupPath + "/" + artifactId + "/" + version + "/" + jarName;

    var outputPath = libDir + File.separator + jarName;

    // Check if file already exists
    if (Files.exists(Paths.get(outputPath))) {
        IO.println("  Already exists: " + jarName);
        return;
    }

    IO.println("  Downloading from: " + url);

    try {
        // Create HttpClient with modern configuration
        var client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

        // Create request with proper headers
        var request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("User-Agent", "LibUp/1.0")
            .GET()
            .timeout(Duration.ofSeconds(30))
            .build();

        // Send request and handle response
        var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            IO.println("  Failed to download: HTTP " + response.statusCode());
            return;
        }

        // Download the file
        try (var in = response.body()) {
            Files.copy(in, Paths.get(outputPath), StandardCopyOption.REPLACE_EXISTING);
            IO.println("  Downloaded: " + jarName);
        }

    } catch (Exception e) {
        IO.println("  Error downloading: " + e.getMessage());
    }
}