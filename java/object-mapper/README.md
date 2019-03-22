# Bean mapping #

## The problem ##

I am looking at bean mappings to clean up hibernate entities.
The goals are:

1. Only keep initialized Entities and Collections
    * Drop not initialized entities, replace them by null
    * Drop not initialized Collections and replace them with there empty versions
1. Get rid of hibernate specific types like PersistentBag, and others
1. Get rid of initialized JavaAssist proxies
1. The possibility to make required configuration within code, no external configuration for the mappings

# [Dozer](https://github.com/DozerMapper/dozer/) #
With the  [CustomFieldMapper](https://github.com/DozerMapper/dozer/blob/master/core/src/main/java/org/dozer/CustomFieldMapper.java) it is possible to achieve my goals.
Before Dozer maps a field it calls my CustomFieldMapper.
Inspecting the parameters I can decide if I want to map the field or opt out and let Dozer do its thing.
When you decide to map the field, it is your responsibility to ensure the deep mapping.


# [ModelMapper](http://modelmapper.org/) #
ModelMapper
I could not find a way to hock into each and every mapping.
To me it seems that I have to write an explicit type mapping for each class I want to check.



## to try ##
[Moo](http://geoffreywiseman.github.io/Moo/)
[transmorph](https://github.com/cchabanois/transmorph/wiki)

## to read ##
[list of bean mappers] (http://www.halyph.com/2013/10/java-object-to-object-mapper.html)
[stackoverflow list of bean mappers] (http://stackoverflow.com/questions/1432764/any-tool-for-java-object-to-object-mapping)