[wiki]: https://wiki.powerplugins.net/wiki/formatter-expansion

# Formatter Expansion
The formatter expansion allows you to turn a long number into a formatted String.  
For example would `123456789` become `123,456,789` using this expansion.

## Placeholder
The placeholder follows a specific pattern that you have to use.  
The format is `%formatter_<options>%` where `<options>` is one of the following options.

### `value:(<value>)`
This option is required as it contains the actual number to format.  
`<value>` can either be a full number or a placeholder using `{}` (`{placeholder}`).  
Note that the placeholder has to return a full number.

### `format:(<format>)`
This option is optional.  
It allows you to set a per-placeholder format, which overrides the default one.

More info on how the formatting works can be found here: https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html

**Note**: The Format also depends on the [locale](#locale) you set!

### `locale:(<locale>)`
The locale option allows you to set the locale on a per-placeholder level.  
The locale is used to determine the format of the number, as some locales/countries have different formattings.

A list of (known) supported locales can be found on the [wiki].

----
## Config
The expansion adds two config options to the config.yml of PlaceholderAPI, which can be altered.

### `format`
The default Format used for the numbers.  
More info on how the formatting works can be found here: https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html

### `locale`
This option is used for the [format](#format).  
Some formats have a different syntax, depending on the locale used.

The locale can be a simple language code (e.g. `en`), or a language code with country code (e.g. `en_US`). 

A list of (known) supported locales can be found on the [wiki].