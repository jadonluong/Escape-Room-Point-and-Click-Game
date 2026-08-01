# Principles of Universal Design

### *1. Equitable Use*

The game offers a guest mode alongside full accounts, so anyone can start playing immediately without the friction of signing up — the core gameplay (exploring rooms, solving puzzles) is identical either way. The one intentional exception is save/resume, reserved for registered accounts, since it requires persistent storage tied to an identity. We haven't yet built accessibility accommodations (colorblind-safe palettes, screen reader support), which would be a meaningful gap to address in future work.

### *2. Flexibility in Use*

Players can toggle music and sound effects independently, and the tiered hint system lets each player choose how much assistance they want — attempt puzzles unaided, or request progressively more revealing hints at their own pace. A future extension could add adjustable text size or a difficulty toggle for puzzle content.

### *3. Simple and Intuitive Use*

The point-and-click interaction model (click an object, get feedback) doesn't require prior gaming experience or reading complex instructions, and consistent visual language (hover highlighting, consistent button styling) is used across every screen. A current limitation: all text is English-only, which reduces intuitiveness for non-English speakers.

### *4. Perceptible Information*

Status messages (login/logout confirmations, errors) use both color and text rather than color alone, and the pause menu explicitly labels consequences in text ("will not save!") rather than relying on icon interpretation. We don't currently support screen readers or audio narration of on-screen text, which would improve this principle for visually impaired users.

### *5. Tolerance for Error*

This is one of our stronger areas: the pause menu deliberately separates Save, Save & Quit, and Quit into distinct, clearly labeled actions with an explicit warning ("game does not auto save") so players can't accidentally lose progress. Modals can be dismissed via Cancel, the × button, clicking outside, or Esc — multiple redundant ways to back out of an action before committing to it.

### *6. Low Physical Effort*
   Interaction is limited to simple mouse clicks with no required precision timing, reflex-based mechanics, or sustained input — puzzles can be paused indefinitely via the pause menu, so there's no physical pressure to act quickly.

### *7. Size and Space for Approach and Use*
   All screens are built on a fixed-ratio canvas that rescales proportionally to fit any window size, so the game remains fully usable whether played in a small window or maximized on a large display, without elements becoming inaccessible or cut off.

# Target Market

We'd market this primarily toward casual puzzle-game enthusiasts and students! People who enjoy short, self-contained mental challenges but don't necessarily want the time commitment of a longer narrative game. The dual-mode structure (a connected Story Mode for players who want a longer arc, and bite-sized Quick Game rooms for a 10–15 minute session) targets both dedicated puzzle fans and casual players looking to fill a short break. Given the game's academic origin and lighthearted theming for one-shot rooms, it would also appeal to younger audiences and could see secondary use in educational or team-building contexts, where short logic puzzles are a common icebreaker activity.

# Demographic Considerations

Several factors could make the program less accessible to certain groups, some of which connect directly to points raised in our ethics modules this term. The game requires a desktop/laptop computer capable of running a Java application, which excludes users who only have access to a smartphone or lower-powered device — a real barrier for lower-income users, echoing broader digital divide concerns. The interface is English-only, disadvantaging non-English speakers, and several puzzle types (ciphers, word-based riddles) assume a level of English literacy that could disadvantage players with dyslexia or those still learning the language. The game also depends on mouse-and-keyboard interaction with no alternative input support, which could exclude players with certain motor impairments, and the lack of screen reader support or colorblind-safe design excludes visually impaired and colorblind users outright. Finally, one feature (the profanity check during signup) requires an active internet connection, meaning offline or low-connectivity users could be blocked from creating an account even though the core game itself doesn't otherwise require internet access.