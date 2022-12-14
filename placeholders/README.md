## Placeholder
You can find enhanced descriptions about the various placeholders, their options and some examples.

### Structure
Each page follows the same structure.

- #### Placeholder pattern
  A display of the placeholder pattern including its optional and required options.  
  Required options are surrounded with `<>` while optional ones are surrounded by `[]`.
  
  This means a placeholder like `%formatter_text_replace_[target]_[replacement]_<text>%` allows no text to be given for the target and/or replacement.  
  The `<>` and `[]` don't need to be included in the placeholder itself.
- #### Options
  A table containing the available options.
  
  Each option can list one of the following attributes:
  - `Type: String|Number` - The type this option represents. Can be a String or Number.
  - `Required: Yes|No` - Whether this option is required (Can't be empty) or not.
  - `Conditions: <list>` - A list of conditions. The `x` in the condition represents the value you provide, so `0 ≤ x` means that your number should be equal to or above zero.
  - `Default: <value>` - The default value that would be used should no option be provided.
- #### Examples
  A list of examples in the format `<command> -> <result>`
