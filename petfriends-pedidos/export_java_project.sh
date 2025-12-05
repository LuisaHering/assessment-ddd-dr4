#!/bin/bash

# Output file
output_file="project_dump.txt"
> "$output_file"

# List of file extensions to include
extensions=("java" "md" "txt" "properties" "xml" "json" "yaml" "yml")

# Build the find command dynamically
find_command="find . -type f \( "
for ext in "${extensions[@]}"; do
    find_command+=" -name '*.$ext' -o"
done
find_command=${find_command::-2}
find_command+=" \) \
! -path './build/*' \
! -path './out/*' \
! -path './.git/*' \
! -path './target/*' \
! -name 'project_dump.txt' \
! -name 'export_java_project.sh'"

# Execute the command and process each file
eval "$find_command" | while read -r file; do
    echo "📄 Processando: ${file#./}"
    echo "=== File: ${file#./} ===" >> "$output_file"

    # 👇 Descomente abaixo se quiser pular arquivos muito grandes (>1MB)
    # if [[ $(wc -c < "$file") -lt 1000000 ]]; then
    #     cat "$file" >> "$output_file"
    # else
    #     echo "[⚠️ Arquivo ignorado: muito grande]" >> "$output_file"
    # fi

    cat "$file" >> "$output_file"
    echo -e "\n" >> "$output_file"
done

echo "✅ Done! Output saved to: $output_file"