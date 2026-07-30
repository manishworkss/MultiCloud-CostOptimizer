import os
import glob

replacements = {
    "'#0b0f19'": "'var(--bg-canvas)'",
    "'#070a12'": "'var(--bg-canvas)'",
    "'#111827'": "'var(--bg-surface)'",
    "'#1e293b'": "'var(--border-color)'",
    "'#334155'": "'var(--border-glow)'",
    "'#f8fafc'": "'var(--text-primary)'",
    "'#94a3b8'": "'var(--text-secondary)'",
    "'#64748b'": "'var(--text-muted)'"
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
        print(f"Updated {file_path}")

print("Done updating colors.")
