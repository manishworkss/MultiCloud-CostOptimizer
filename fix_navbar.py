import glob

replacements = {
    "'#1e293b'": "'var(--border-color)'",
    "'#94a3b8'": "'var(--text-secondary)'"
}

jsx_files = glob.glob('frontend/src/**/*.jsx', recursive=True)

for file_path in jsx_files:
    with open(file_path, 'r') as f:
        content = f.read()
    
    original_content = content
    for old, new in replacements.items():
        content = content.replace(old, new)
        
    if content != original_content:
        with open(file_path, 'w') as f:
            f.write(content)
        print(f"Fixed borders in {file_path}")

print("Done fixing borders.")
