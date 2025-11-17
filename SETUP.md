# Repository Setup Instructions

## Steps to Set Up Private Repository

### 1. Create Private Repository on GitHub

- Go to https://github.com/new
- Set repository name
- Select **Private** visibility
- Do NOT initialize with README (you already have files)
- Click "Create repository"

### 2. Commit and Push Your Code

```bash
# Commit your staged files
git commit -m "Initial commit: Weather service project"

# Add remote (replace with your actual repo URL)
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git

# Rename branch to main (if needed)
git branch -M main

# Push to GitHub
git push -u origin main
```

### 3. Add Teacher as Collaborator

1. Go to your repository on GitHub
2. Click **Settings** → **Collaborators** (in left sidebar)
3. Click **Add people**
4. Enter teacher's GitHub username or email
5. Click **Add [username] to this repository**
6. Teacher will receive email invitation

## Important Notes

- Repository is **private** - only you and collaborators can see it
- Teacher needs a GitHub account to be added as collaborator
- The `server/target/` folder is automatically excluded (via .gitignore)
- Never commit sensitive information (passwords, API keys, etc.)
