# WokePlugin

Paper plugin that adds user-definable nicknames and pronouns.

Overrides the default chat renderer, which will likely break other plugins.

## Features

- Pronouns are displayed in chat and in the tab list.
- Nicknames appear only in chat.

  - Hovering over a nicknamed player's chat message will show their actual username.

## Commands

### Pronouns

`/pronouns set <pronoun>`

- Set your pronouns.
- `<pronoun>` must be supplied in a "subject/object" format, with the exception of "any".

`/pronouns clear`

- Clear your pronouns.

### Nicknames

`/nickname set <nickname>`

- Set your nickname.

- `<nickname>` may not contain spaces.

`/nickname clear`

- Clear your nickname.

### Colors

`/pronouns color <color name>`

`/nickname color <color name>`

- Set the color your nickname will be displayed with. Defaults to gray.

`nickname color hex <hex color>`

`/pronouns color hex <hex color>`

- Optional support for hex colors.
- Can be supplied with or without a leading `#`.
