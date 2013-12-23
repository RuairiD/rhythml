grammar RhythML;
p : rhy ;
rhy : inst rhy | inst;
inst : Id ':' bar;
Id : 'B' | 'SN' | 'HT' | 'LT' | 'FT' | 'HH' | 'Rd' | 'CC' ;
bar : '[' beats ']' ;
beats : Beat beats | Beat ;
Beat : 'x' | 'X' | 'o' | 'O' | '-' ;
WS : [ \t\r\n]+ -> skip ;
