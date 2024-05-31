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
tiger_program: PROGRAM ID LET declaration_segment BEGIN funct_list? END EOF;

declaration_segment: type_declaration_list? var_declaration_list?;

type_declaration_list: type_declaration type_declaration_list?;

type_declaration: TYPE ID TASSIGN type SEMICOLON;

type: base_type #BaseType | ARRAY OPENBRACK INTLIT CLOSEBRACK OF base_type #ArrayType | ID #UserType;

base_type: INT;

var_declaration_list: var_declaration var_declaration_list?;

var_declaration: storage_class id_list COLON type optional_init? SEMICOLON;

storage_class: VAR | STATIC;

id_list: ID id_list_tail?;

id_list_tail: COMMA id_list;

optional_init: ASSIGN const;

funct_list: funct funct_list?;

funct: FUNCTION ID OPENPAREN param_list? CLOSEPAREN ret_type? BEGIN stat_seq END;

param_list: param param_list_tail?;

param: ID COLON type ;

param_list_tail: COMMA param param_list_tail?;

ret_type: COLON type;

stat_seq: stat stat_seq_tail?;

stat_seq_tail: stat_seq;

stat: value ASSIGN expr SEMICOLON                               #AssignStat
    | IF expr THEN stat_seq if_statement_tail                   #IfStat
    | WHILE expr DO stat_seq ENDDO SEMICOLON                    #WhileStat
    | FOR ID ASSIGN expr TO expr DO stat_seq ENDDO SEMICOLON    #ForStat
    | optprefix? ID OPENPAREN expr_list? CLOSEPAREN SEMICOLON   #FunctCallStat
    | BREAK SEMICOLON                                           #BreakStat
    | RETURN optreturn? SEMICOLON                               #ReturnStat
    | LET declaration_segment BEGIN stat_seq END                #LetStat
    ;

if_statement_tail: ENDIF SEMICOLON
    | ELSE stat_seq ENDIF SEMICOLON;

optreturn: expr;

optprefix: value ASSIGN;

expr_list: expr expr_list_tail?;

expr_list_tail: COMMA expr expr_list_tail?;

expr: and_expr expr_tail?;

expr_tail: OR and_expr expr_tail?;

and_expr: comp_expr and_expr_tail?;

and_expr_tail: AND comp_expr and_expr_tail?;

comp_expr: term comp_expr_tail?;

comp_expr_tail: comp_op term comp_expr_tail?;

comp_op: EQUAL | NEQUAL | LESS | GREAT | LESSEQ | GREATEQ;

term: factor term_tail?;

term_tail: (PLUS|MINUS) factor term_tail?;

factor: atom factor_tail?;

factor_tail: (MULT|DIV) atom factor_tail?;

atom: base atom_tail?;

atom_tail: POW atom;

base: OPENPAREN expr CLOSEPAREN
    | value
    | const;

const: INTLIT;

value: ID value_tail?; // Variable access

value_tail: OPENBRACK expr CLOSEBRACK; // Array access
