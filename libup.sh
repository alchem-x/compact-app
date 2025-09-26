#!/bin/bash

# LibUp Shell Version - Downloads dependencies from Maven repository

MAVEN_REPO="${MAVEN_REPO:-https://repo1.maven.org/maven2}"
echo "Using Maven repository: $MAVEN_REPO"
echo "Starting dependency download..."

mkdir -p lib

while IFS= read -r line || [[ -n "$line" ]]; do
    dep=$(echo "$line" | xargs)
    if [[ -n "$dep" && ! "$dep" =~ ^[[:space:]]*# ]]; then
        echo "Processing: $dep"

        IFS=':' read -ra parts <<< "$dep"
        if [[ ${#parts[@]} -ne 3 ]]; then
            echo "  Invalid dependency format: $dep"
            continue
        fi

        group_id="${parts[0]}"
        artifact_id="${parts[1]}"
        version="${parts[2]}"
        jar_name="${artifact_id}-${version}.jar"
        output_path="lib/$jar_name"

        if [[ -f "$output_path" ]]; then
            echo "  Already exists: $jar_name"
            continue
        fi

        url="$MAVEN_REPO/${group_id//\./\//}/$artifact_id/$version/$jar_name"
        echo "  Downloading from: $url"

        if curl -sSf -L -A "LibUp/1.0" --max-time 30 -o "$output_path" "$url"; then
            echo "  Downloaded: $jar_name"
        else
            echo "  Error downloading $jar_name"
            rm -f "$output_path"
        fi
    fi
done < lib.txt

echo "All dependencies downloaded successfully!"