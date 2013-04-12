1. create wrapping class that extends the class to start from
1. Replace Constructor calls
    1. Replace two argument constructor
        * new SimpleDateFormat($string$, $locale$)
        * new FormatWrapper($string$, $locale$)
    1. Replace one argument constructor
        * new SimpleDateFormat($string$)
        * new FormatWrapper($string$)
1. Switch variable declarations over to the Migration type
    1. Declarations that are initialized on Declaration
        * SimpleDateFormat $variablename$ = new FormatWrapper($param$);
        * FormatWrapper $variablename$ = new FormatWrapper($param$);
