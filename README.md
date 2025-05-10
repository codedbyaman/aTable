<p align="center">
  <img src="https://github.com/user-attachments/assets/ad2d946d-a72d-4112-a7d0-839f08a0bed6" width="140" alt="aTable logo"/>
</p>

<p align="center"><em>Learn math tables the fun way 🧮</em></p>
<p align="center">
  Powered by <strong>Material You</strong> (dynamic light / dark theme), bilingual Text‑to‑Speech, haptic feedback & quizzes.
</p>

<p align="center">
  <img src="https://img.shields.io/github/license/codedbyaman/aTable?style=for-the-badge" alt="License"/>
  <img src="https://img.shields.io/github/actions/workflow/status/codedbyaman/aTable/android.yml?style=for-the-badge&label=CI" alt="CI"/>
  <img src="https://img.shields.io/github/stars/codedbyaman/aTable?style=for-the-badge&color=yellow" alt="Stars"/>
  <img src="https://img.shields.io/badge/MinSDK-21%2B-blueviolet?style=for-the-badge" alt="Min SDK"/>
</p>

---

## 📑 Contents
- [Overview](#overview)
- [Features](#features)
- [Quick Start](#quick-start)
- [Architecture](#architecture)
- [Contributing](#contributing)
- [Roadmap](#roadmap)
- [Support & Feedback](#support--feedback)
- [Contact](#contact)
- [License](#license)

---

## 🖼️ Overview
aTable is a lightweight Android app for **students**, **teachers** and **parents** to practise, quiz and *listen to* tables in English or Hindi.

Built with **Kotlin 1.9**, **AGP 8**, **Material 3** and follows Google’s [modern Android guidelines](https://developer.android.com/guide).

---

## ✨ Features

| Category | Highlights |
| -------- | ---------- |
| **Learn & Practise** | 🎙️&nbsp;Dual‑language Text‑to‑Speech<br/>📳&nbsp;Haptic feedback on inputs & correct / wrong answers<br/>🖋️&nbsp;Chalk‑board theme that adapts to Material You |
| **Quiz** | ⏱️&nbsp;Timed rounds with score & streak<br/>📈&nbsp;Adaptive difficulty (larger numbers as you improve) |
| **Accessibility & UX** | 🔊&nbsp;Full TalkBack labels & scalable fonts<br/>🎨&nbsp;Automatic light / dark theme<br/>✨&nbsp;Adaptive launcher & shortcut icons |

---

## ⚡ Quick Start

```bash
# 1. Clone your fork
git clone https://github.com/<you>/aTable.git && cd aTable

# 2. Run on a connected device / emulator
./gradlew installDebug
```

> **Requires:** Android Studio **Hedgehog 2023.3.1**+ and Android SDK 34.

---

## 🏗️ Architecture

```text
app/
 ├── data/          # DataStore helpers, multiplication logic
 ├── ui/            # Activities & XML (Compose migration WIP)
 ├── di/            # Simple service‑locator
 └── util/          # Extensions, constants
```

*MVVM‑lite*, single‑module, Jetpack‑only stack.

---

## 🤝 Contributing

1. **Fork → create `feat/<xyz>` branch → commit → push → PR**
2. Follow **[Conventional Commits](https://www.conventionalcommits.org)** (e.g. `feat: add dark‑mode toggle`)
3. Format & lint **before push**:

   ```bash
   ./gradlew ktlintFormat
   ```

4. Describe your change and link any related issue.

---

## 🛣️ Roadmap

- [ ] Jetpack Compose UI rewrite  
- [ ] Firebase Cloud Sync (progress backup)  
- [ ] Customisable quiz lengths  
- [ ] Unit & UI tests with Paparazzi / Espresso  

---

## 🙋‍♂️ Support & Feedback

Feel free to **open an issue** or **vote with 👍 reactions** if you’d like a feature prioritised or spot a bug &mdash; every suggestion helps!

---

## 📬 Contact

|            | Links |
| ---------- | ----- |
| **Aman**   | [LinkedIn](https://www.linkedin.com/in/aman-kumar-2a809753/) • [Twitter / X](https://twitter.com/codedbyaman) |

> **Project URL:** <https://github.com/codedbyaman/aTable>

---

## 📜 License

This project is licensed under the **MIT License** &mdash; see the full text in [`LICENSE`](LICENSE).

---

<p align="center"><sub>Made with ❤️ and ☕ by <a href="https://github.com/codedbyaman">@codedbyaman</a></sub></p>
