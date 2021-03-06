# Message Masking SDK Release Notes

## Version 2.1.3 - 12 April 2019

### Fixed

* Previously, attempts to mask track 2 data that contains no field separators would result in an 
`IllegalStateException`. There are legitimate cases, however, where track 2 data does not have any field separators and 
masking should not be disrupted. In such cases, the `MaskAll` scheme is applied.

## Version 2.1.1 - 22 January 2019

### Fixed

* Fixed a bug in `JsonMaskingUtil` where null literals were not considered as one of the possible values of a JSON 
field. The onus for handling null input appropriately is on implementations of `Masker`. 

## Version 2.0.0 - 30 August 2018

### Breaking changes

* The `io.electrum.sdk.masking` package was removed and all of the classes that were previously in that package are now
found in the `io.electrum.sdk.masking2` package. 

### New Features

* JSON masking using [JsonPath](https://github.com/json-path/JsonPath) to specify fields to be masked.

### Fixed

* Previously, XML document nodes that contain only whitespace would be masked when referred to by XPath. This can be a 
problem when masking XML that is already pretty-printed as whitespace characters between XML elements are considered 
text nodes. This has been fixed: no whitespace-only node will be masked.  
* Bug where PANs of length 10 do not get masked at all has been fixed. 

## Version 1.0.0 - 25 July 2018

Initial release. 

While this version is reflected in the git history of the project, it was not officially released. 