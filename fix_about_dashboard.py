import os

file_path = 'frontend/src/components/AboutProjectDashboard.jsx'
with open(file_path, 'r') as f:
    content = f.read()

# Replace hardcoded white rgba overlays with CSS variables
content = content.replace("'rgba(255,255,255,0.03)'", "'var(--bg-glass)'")
content = content.replace("'rgba(255,255,255,0.05)'", "'var(--border-color)'")
content = content.replace("'rgba(0,0,0,0.2)'", "'0 10px 40px rgba(0, 0, 0, 0.05)'") # soft shadow

with open(file_path, 'w') as f:
    f.write(content)

print("Fixed AboutProjectDashboard")
