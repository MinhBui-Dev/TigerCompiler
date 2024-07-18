#!/bin/bash

# Define the directory containing the files
test_dir="phase2_testfiles_v1/files"

# Define your Java command (replace with your actual command)
java_command="java -jar cs8803_bin/tigerc.jar -f"  # Assuming -f takes the filename with path

# Loop through all files in the src directory (excluding hidden ones)
for file in "$test_dir/"*.tiger ; do
  # Check if it's a regular file (not a directory or hidden file)
  if [[ -f "$file" ]]; then
    # Construct the full filename with path
    filename="$file"

    # Run the Java command with the full path filename and -i flag after
    echo "Running: $java_command $filename -i"  # Add echo for demonstration
    $java_command "$filename" -i
  fi
done

echo "Finished processing .tiger files in $test_dir"