#!/bin/bash

# Hello Java Runner Script
# This script runs the Hello.java compact application with proper classpath

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
JAVA_FILE="Hello.java"
LIB_DIR="lib"
LIB_FILE="lib.txt"

print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Java is available
check_java() {
    if ! command -v java &> /dev/null; then
        print_error "Java is not installed or not in PATH"
        exit 1
    fi

    local java_version=$(java -version 2>&1 | head -n1 | cut -d'"' -f2)
    print_info "Using Java version: $java_version"
}

# Check if required files exist
check_files() {
    if [[ ! -f "$JAVA_FILE" ]]; then
        print_error "Java file '$JAVA_FILE' not found"
        exit 1
    fi

    if [[ ! -d "$LIB_DIR" ]]; then
        print_warning "Library directory '$LIB_DIR' not found"
        print_info "Creating library directory..."
        mkdir -p "$LIB_DIR"
    fi

    if [[ ! -f "$LIB_FILE" ]]; then
        print_warning "Dependency file '$LIB_FILE' not found"
        print_info "You need to run LibUp.java first to download dependencies"
    fi
}

# Download dependencies if needed
download_dependencies() {
    local jar_count=$(find "$LIB_DIR" -name "*.jar" 2>/dev/null | wc -l)

    if [[ $jar_count -eq 0 ]]; then
        if [[ -f "$LIB_FILE" ]]; then
            print_info "No JAR files found in $LIB_DIR, downloading dependencies..."
            if java LibUp.java; then
                print_success "Dependencies downloaded successfully"
            else
                print_error "Failed to download dependencies"
                exit 1
            fi
        else
            print_error "No dependencies found and $LIB_FILE is missing"
            print_info "Please create $LIB_FILE with your dependencies and run LibUp.java first"
            exit 1
        fi
    else
        print_info "Found $jar_count JAR files in $LIB_DIR"
    fi
}

# Run HelloWorld
run_hello() {
    print_info "Running $JAVA_FILE..."
    echo "=========================================="

    # Run with proper classpath
    if java -cp "$LIB_DIR/*" "$JAVA_FILE"; then
        echo "=========================================="
        print_success "Hello executed successfully!"
    else
        echo "=========================================="
        print_error "Failed to run Hello"
        exit 1
    fi
}

# Main execution
main() {
    echo "🚀 Hello Java Runner"
    echo "=========================="

    check_java
    check_files
    download_dependencies
    run_hello

    echo ""
    print_success "All done! ✨"
}

# Handle command line arguments
case "${1:-}" in
    "--help"|"-h")
        echo "Usage: $0 [OPTIONS]"
        echo ""
        echo "Options:"
        echo "  --help, -h     Show this help message"
        echo "  --clean        Clean lib directory before running"
        echo ""
        echo "Environment Variables:"
        echo "  MAVEN_REPO     Custom Maven repository URL"
        echo ""
        echo "Examples:"
        echo "  $0                    # Run normally"
        echo "  $0 --clean           # Clean and run"
        echo "  MAVEN_REPO=https://nexus.company.com $0  # Use custom repo"
        exit 0
        ;;
    "--clean")
        print_info "Cleaning lib directory..."
        rm -f "$LIB_DIR"/*.jar
        print_success "Lib directory cleaned"
        main
        ;;
    "")
        main
        ;;
    *)
        print_error "Unknown option: $1"
        echo "Use --help for usage information"
        exit 1
        ;;
esac