import glob

replacements = {
    "'rgba(17, 24, 39, 0.4)'": "'var(--bg-surface)'",
    "'rgba(17, 24, 39, 0.6)'": "'var(--bg-surface)'",
    "'rgba(17, 24, 39, 0.9)'": "'var(--bg-glass)'",
    "'rgba(15, 23, 42, 0.95)'": "'var(--bg-glass)'",
    "'rgba(255,255,255,0.08)'": "'var(--border-color)'",
    "'rgba(255, 255, 255, 0.08)'": "'var(--border-color)'",
    "'rgba(255,255,255,0.05)'": "'var(--border-color)'",
    "'rgba(255, 255, 255, 0.05)'": "'var(--border-color)'",
    "'rgba(255,255,255,0.1)'": "'var(--border-color)'",
    "'rgba(255, 255, 255, 0.1)'": "'var(--border-color)'",
    "'rgba(255,255,255,0.03)'": "'var(--bg-surface-hover)'",
    "'rgba(9, 13, 22, 0.85)'": "'rgba(255, 255, 255, 0.85)'",
    "'rgba(0,0,0,0.2)'": "'rgba(0, 0, 0, 0.05)'",
    "'#cbd5e1'": "'var(--text-secondary)'",
    "background: '#111827'": "background: 'var(--bg-surface)'",
    "color: '#f8fafc'": "color: 'var(--text-primary)'"
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
        print(f"Fixed RGBA in {file_path}")

print("Done fixing RGBA.")
