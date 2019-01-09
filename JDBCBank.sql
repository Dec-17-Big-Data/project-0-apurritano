--Austin Purritano
--JDBC Bank Assignment
--SQL teardown & setup

--TEARDOWN: 

alter table accounts drop constraint user_id_foreign_key;

drop table users;
drop table accounts;
drop table transactions;

drop sequence account_id_sequence;
drop sequence user_id_sequence;

drop procedure delete_account;
drop procedure deposit_account;
drop procedure withdraw_account;
commit;


--SETUP:

create table users (
    user_id number(10) primary key,
    full_name varchar2(255) not null,
    username varchar2(255) unique not null,
    username_password varchar2(255) not null,
    user_type varchar2(255) not null
);

create table accounts(
    account_id number(10) primary key,
    account_type varchar2(255) not null,
    account_balance real not null,
    user_id number(10)    
);

create table transactions(
    transaction_id number(10) primary key,
    transaction_type varchar2(255) not null,
    amount binary_float default '0',
    date_of_tranaction timestamp,
    account_id number(10)
);

alter table accounts add constraint user_id_foreign_key
    foreign key (user_id) references users (user_id);
    
create sequence user_id_sequence
    minvalue 100
    start with 100
    increment by 2
    cache 25;

create sequence account_id_sequence
    minvalue 200
    start with 200  
    increment by 2
    cache 25;

INSERT INTO users(
    user_id,
    full_name,
    username,
    username_password,
    user_type
)
Values(
    123,
    'Austin P',
    'apurritano',
    'p4ssw0rd',
    'super'
);

INSERT INTO accounts (
    account_id,
    account_type,
    account_balance,
    user_id
)
values (
    321,
    'Checking',
    500,
    123
);

INSERT INTO accounts (
    account_id,
    account_type,
    account_balance,
    user_id
)
values (
    213,
    'Savings ',
    1500,
    123
);
    
INSERT INTO users(
    user_id, 
    full_name, 
    username, 
    username_password, 
    user_type
) 
values (
    456, 
    'Jack Daniels', 
    'jdaniels', 
    'whiskey8', 
    'normal'
);

INSERT INTO accounts (
    account_id,
    account_type,
    account_balance,
    user_id
)
values (
    654,
    'Checking',
    100,
    456
    );

INSERT INTO accounts (
    account_id,
    account_type,
    account_balance,
    user_id
)
values (
    546,
    'Savings ',
    250,
    456
);

INSERT INTO users(
    user_id, 
    full_name, 
    username, 
    username_password, 
    user_type
) 
values (
    789, 
    'Captain Morgan', 
    'capmorgan', 
    'rum45678', 
    'normal'
);

INSERT INTO accounts (
    account_id,
    account_type,
    account_balance,
    user_id
)
values (
    987,
    'Checking',
    100,
    789
    );

INSERT INTO accounts (
    account_id,
    account_type,
    account_balance,
    user_id
)
values (
    879,
    'Savings ',
    250,
    789
);

INSERT INTO users(
    user_id,
    full_name,
    username,
    username_password,
    user_type
)
Values(
    987,
    'Jose Cuervo',
    'josecuervo',
    'tequila8',
    'normal'
);


create or replace PROCEDURE DELETE_ACCOUNT 
    (account_id_in IN NUMBER) 
    AS a_balance NUMBER(20);
BEGIN
    select account_balance into a_balance 
    from accounts
    where account_id = account_id_in;
    if a_balance = 0
    then 
        delete from accounts 
        where account_id = account_id_in;
        commit;
    end if;
END;
/ 
   
CREATE OR REPLACE PROCEDURE WITHDRAW_ACCOUNT 
(
    account_id_in IN NUMBER,
    withdraw_amount_in IN NUMBER
) AS 
    l_balance NUMBER(20);
BEGIN
    select account_balance into l_balance 
    from accounts
    where account_id = account_id_in;
    if l_balance >= withdraw_amount_in
    then 
        update accounts 
        set account_balance = l_balance - withdraw_amount_in
        where account_id = account_id_in;
        commit;
    end if;
END WITHDRAW_ACCOUNT;
/

create or replace PROCEDURE DEPOSIT_ACCOUNT 
(
    account_id_in IN NUMBER,
    deposit_amount_in IN NUMBER
) AS 
    d_balance NUMBER(20); 
BEGIN
    select account_balance into d_balance 
    from accounts
    where account_id = account_id_in;
    if deposit_amount_in > 0
    then 
        update accounts 
        set account_balance = d_balance + deposit_amount_in
        where account_id = account_id_in;

        commit;
    end if;
END DEPOSIT_ACCOUNT;
/

commit;