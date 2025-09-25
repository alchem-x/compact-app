import module java.base;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.base.Joiner;

void main() {
    // Demonstrate Commons Lang3 usage
    IO.println("=== Commons Lang3 Demo ===");
    var text = "Hello World from Commons Lang3!";
    IO.println("Original text: " + text);
    IO.println("Reversed: " + StringUtils.reverse(text));
    IO.println("Capitalized: " + StringUtils.capitalize(text));
    IO.println("Abbreviated: " + StringUtils.abbreviate(text, 20));

    // Demonstrate Guava usage
    IO.println("\n=== Guava Demo ===");
    var names = Lists.newArrayList("Alice", "Bob", "Charlie", "David");
    IO.println("Original list: " + names);

    // Transform with Guava
    var upperNames = Lists.transform(names, String::toUpperCase);
    IO.println("Uppercase names: " + upperNames);

    // Join with Guava
    var joined = Joiner.on(" | ").join(names);
    IO.println("Joined names: " + joined);

    // Map with Guava
    var nameLengths = Maps.newHashMap();
    for (var name : names) {
        nameLengths.put(name, name.length());
    }
    IO.println("Name lengths: " + nameLengths);

    // Show some practical usage combining all libraries
    IO.println("\n=== Combined Demo ===");
    var data = List.of("apple", "banana", "cherry", "date", "elderberry");

    // Filter and transform with Guava
    var filtered = data.stream()
        .filter(fruit -> fruit.length() > 5)
        .collect(java.util.stream.Collectors.toList());

    IO.println("Fruits with >5 characters: " + filtered);

    // Process with Commons Lang
    var processed = filtered.stream()
        .map(fruit -> StringUtils.capitalize(fruit) + " (" + fruit.length() + ")")
        .collect(java.util.stream.Collectors.toList());

    IO.println("Processed fruits: " + processed);

    IO.println("\n=== Hello World Complete! ===");
    IO.println("This compact Java file used:");
    IO.println("- Commons Lang3 for string manipulation");
    IO.println("- Guava for collections and utilities");
    IO.println("- JEP 512 compact file format");
    IO.println("- Modern Java features like var and HttpClient");
}