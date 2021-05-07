# Changelog
This file lists the different changes of the Formatter-expansion in detail.

## `1.7.0`
- Switch from `long` to `BigDecimal` to allow larger numbers with decimals (#2)
- Always change `{{u}}` into `_` and not just when the text is only `{{u}}`
- Removed `join` option. This was deprecated since [1.6.0](#160)

## `1.6.0`
- Add `replace` placeholder.  
  It supports empty String for `<replacement>` and `{{u}}` for underlines.
  ```yaml
  %formatter_text_replace_<target>_<replacement>_<text>%
  ```
- Deprecated `join` placeholder.  
  Will be removed in next update. Also prints warnings about that when used and displays `replace` alternative.

## `1.5.0`
- Changed `number_format` placeholder structure.
  ```yaml
  # Old
  #
  %formatter_number_format_<number>[_<option>]%
  
  # New
  #
  %formatter_number_format_<number>%
  %formatter_number_format_[locale]:[format]_<number>%
  ```
- Changed `text_join` placeholder structure to allow own target system.
  ```yaml
  # Old (Only combines text separated by Spaces)
  #
  %formatter_text_join_<separator>_<text>%
  
  # New (Define your own cridentials)
  #
  %formatter_text_join_<target>_<separator>_<text>%
  ```

## `1.4.1`
- Fix `time_<number>` and `time_<secs|seconds>_<number>` not returning the right value.

## `1.4.0`
- Added placeholders for `time` to treat the number as different time.
  ```yaml
  # Same as %formatter_number_time_<number>%
  #
  %formatter_number_time_seconds_<number>%
  %formatter_number_time_secs_<number>%
  
  # Treats the number as minutes
  #
  %formatter_number_time_minutes_<number>%
  %formatter_number_time_mins_<number>%
  
  # Treats the number as hours
  #
  %formatter_number_time_hours_<number>%
  %formatter_number_time_hrs_<number>%
  ```

## `1.3.2`
- Changed config option `condensed` from String to boolean. Old String is still supported.
  ```yaml
  formatter:
    time:
      condensed: false # New
  ```
- Changed locale format from `lang:country` to `lang-country`.

## `1.3.1`
- New Text option `join` (Combines space-separated text with the provided character(s))
  ```yaml
  %formatter_text_join_<characters>_<text>%
  ```

## `1.3.0`
- Updated to PlaceholderAPI 2.10.7 and Spigot 1.16.1

## `1.2.0`
- Added Support for formatting Text.
- Switched locale Placeholder from using a `_` to using `:`
- Placeholder structure has been changed:
  ```yaml
  %formatter_text_substring_[start]:[end]_<text>%
  %formatter_text_uppercase_<text>%
  %formatter_text_lowercase_<text>%
  
  %formatter_number_format_<number>[_<options>]%
  %formatter_number_time_<number>%
  ```

## `1.1.1`
- Fix `time` placeholder not working with large numbers.

## `1.1.0`
- Added `time` placeholder to return duration from provided number.
  ```yaml
  %formatter_time_value:(<number>)%
  ```
- New config options for the time placeholder.
  ```yaml
  formatter:
    time:
      hours: h
      seconds: s
      days: d
      condensed: 'no'
      minutes: m
  ```

## `1.0.1`
- Made options for `format` placeholder case-insensitive.

## `1.0.0`
- First release.

Placeholders:
```yaml
%formatter_value:(<number>)%
%formatter_value:(<number>)_format:(<format>)%
%formatter_value:(<number>)_locale:(<locale>)%
%formatter_value:(<number>)_format:(<format>)_locale:(<locale>)%
```