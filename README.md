# WokePlugin

Paper plugin that adds user-definable nicknames and pronouns.

Overrides the default chat renderer, which will likely break other plugins.

## Features

- Pronouns and nicknames are displayed in chat and in the tab list.
- Hovering over a nicknamed player's chat message will show their actual username.

## Commands

### Pronouns

`/pronouns set <pronouns>`

- Set your pronouns.
- `<pronouns>` must be supplied in a "subject/object" format, with the exception of "any".

`/pronouns clear`

- Clear your pronouns.

### Nicknames

`/nickname set <nickname>`

- Set your nickname.

- `<nickname>` may not contain spaces.

`/nickname clear`

- Clear your nickname.

`/nickname` is aliased to `/nick` for convenience.

### Colors

`/pronouns color <color name|#hex color|reset>`

`/nickname color <color name|#hex color|reset>`

- Sets/clears the color of your pronouns/nickname.
- Accepts either a named color (of which there are only a few) or a hex color (preceded by a `#`).