grammar Tiger;

@header {
    package antlr;
}
/* Lexer Rules */
/* Keywords */
ARRAY: 'array';
BEGIN: 'begin';
BREAK: 'break';
DO: 'do';
ELSE: 'else';
END: 'end';
ENDDO: 'enddo';
ENDIF: 'endif';
FOR: 'for';
FUNCTION: 'function';
IF: 'if';
INT: 'int';
LET: 'let';
OF: 'of';
PROGRAM: 'program';
RETURN: 'return';
STATIC: 'static';
THEN: 'then';
TO: 'to';
TYPE: 'type';
VAR: 'var';
WHILE: 'while';

/* Punctuation */
COMMA: ',';
DOT: '.';
COLON: ':';
SEMICOLON: ';';
OPENPAREN: '(';
CLOSEPAREN: ')';
OPENBRACK: '[';
CLOSEBRACK: ']';
OPENBRACE: '{';
CLOSEBRACE: '}';

/* Binary Operators */
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
POW: '**';
EQUAL: '==';
NEQUAL: '!=';
LESS: '<';
GREAT: '>';
LESSEQ: '<=';
GREATEQ: '>=';
AND: '&' ;
OR: '|';

/* Assignment Operators */
ASSIGN: ':=';
TASSIGN: '=';

ID: [a-zA-Z][a-zA-Z0-9_]*;
INTLIT: [0] | [1-9][0-9]*;
COMMENT: '/*' .*? '*/' -> channel(HIDDEN);
WS: [ \t\r\n]+ -> skip;

/* Parser Rules */
tiger_program: PROGRAM ID LET declaration_segment BEGIN funct_list END EOF;

declaration_segment: type_declaration_list var_declaration_list;

type_declaration_list: type_declaration*;

var_declaration_list: var_declaration*;

funct_list: funct*;

type_declaration: TYPE ID TASSIGN type SEMICOLON;

type: base_type                                         # type_base
    | ARRAY OPENBRACK INTLIT CLOSEBRACK OF base_type    # type_array
    | ID                                                # type_id;

base_type: INT;

var_declaration: storage_class id_list COLON type optional_init? SEMICOLON;

storage_class: VAR | STATIC;

id_list: ID (COMMA ID)*;
optional_init: ASSIGN const;

funct: FUNCTION ID OPENPAREN param_list? CLOSEPAREN ret_type BEGIN stat_seq END;

param_list: param (COMMA param)* ;

param: ID COLON type ;

ret_type: (COLON type)?;

stat_seq: (statNode)+ ;

statNode: value ASSIGN expr SEMICOLON                                           #AssignStat
    | IF expr THEN stat_seq ENDIF SEMICOLON                                     #IfStat
    | IF expr THEN lblock=stat_seq ELSE rblock=stat_seq ENDIF SEMICOLON         #IfElseStat
    | WHILE expr DO stat_seq ENDDO SEMICOLON                                    #WhileStat
    | FOR ID ASSIGN start=expr TO end=expr DO stat_seq ENDDO SEMICOLON          #ForStat
    | (optprefix)? ID OPENPAREN expr_list CLOSEPAREN SEMICOLON                   #FunctCallStat
    | BREAK SEMICOLON                                                           #BreakStat
    | RETURN optreturn? SEMICOLON                                               #ReturnStat
    | LET declaration_segment BEGIN stat_seq END                                #LetStat
    ;

optreturn: expr;

optprefix: value ASSIGN;

expr_list: (expr (COMMA expr)*)?;

expr: OPENPAREN expr CLOSEPAREN                                         #ParenExpr
    |<assoc=right> expr POW expr                                        #PowerExpr
    | expr (MULT|DIV) expr                                              #MultExpr
    | expr (PLUS|MINUS) expr                                            #AddExpr
    | expr (EQUAL | NEQUAL | LESS | GREAT | LESSEQ | GREATEQ) expr      #CompExpr
    | expr AND expr                                                     #AndExpr
    | expr OR expr                                                      #OrExpr
    | value                                                             #ValueExpr
    | const                                                             #ConstExpr;

const: INTLIT;

value: ID #VariableAccess
     | ID OPENBRACK expr CLOSEBRACK #ArrayAccess;