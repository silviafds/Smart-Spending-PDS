CREATE TABLE categoria_receita (
      id serial4 NOT NULL,
      nome text NULL,
      CONSTRAINT categoria_receita_pkey PRIMARY KEY (id)
);

insert into categoria_receita (nome)
values ('Proventos'),
       ('Investimento'),
       ('Reembolso'),
       ('Estorno'),
       ('Vendas'),
       ('Prestação de Serviço'),
       ('Filantropia');

CREATE TABLE conta_interna (
      id bigserial NOT NULL,
      nome varchar(255) NULL,
      desabilitar_conta bool NULL,
      CONSTRAINT conta_interna_pkey PRIMARY KEY (id)
);

insert into conta_interna (nome, desabilitar_conta)
values ('Conta 1', true),
       ('Conta 2', false),
       ('Conta 3', false),
       ('Conta 4', false),
       ('Conta 5', false);

CREATE TABLE origem (
    id serial NOT NULL,
    nome text NULL,
    CONSTRAINT origem_pkey PRIMARY KEY (id)
);
insert into origem (nome)
values ('Cheque'),
       ('Papel e moeda'),
       ('Transferência'),
       ('Pix');

CREATE TABLE users (
      id bigserial NOT NULL,
      datanascimento date NULL,
      email varchar(255) NULL,
      login varchar(255) NULL,
      nome varchar(255) NULL,
      "password" varchar(255) NULL,
      "role" varchar(255) NULL,
      sobrenome varchar(255) NULL,
      CONSTRAINT users_pkey PRIMARY KEY (id),
      CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'USER'::character varying])::text[])))
);

CREATE TABLE titulos_contabeis_receita (
      id bigserial NOT NULL,
      nome text NULL,
      categoria_receita_id int4 NULL,
      CONSTRAINT titulos_contabeis_receita_pkey PRIMARY KEY (id),
      CONSTRAINT titulos_contabeis_receita_categoria_receita_id_fkey FOREIGN KEY (categoria_receita_id) REFERENCES public.categoria_receita(id)
);

insert into titulos_contabeis_receita (nome, categoria_receita_id)
values ('Salário', 1),
       ('Pensão', 1),
       ('Recebimento de aluguel imobiliário', 1),
       ('Lucro de dividendos', 2),
       ('Lucro de venda de ações', 2),
       ('Lucro com moedas', 2),
       ('Rendimento de juros', 2),
       ('Lucro de dividendos', 2),
       ('Dividendos', 2),
       ('Farmácia', 3),
       ('Perfumaria', 3),
       ('Alimentação', 3),
       ('Vestimenta', 3),
       ('Consulta médica', 3),
       ('Estorno bancário', 4),
       ('Estorno compra', 4),
       ('Cashback', 4),
       ('Automóvel', 5),
       ('Imóvel', 5),
       ('Produto Acabado', 5),
       ('Produto Semi-acabado', 5),
       ('Recebimento de Comissão', 6),
       ('Royalties', 6),
       ('Recebimento de Seguros', 6),
       ('Recebimento Por Manutenção', 6),
       ('Doação', 7),
       ('Presente', 7);


CREATE TABLE receita (
    id bigserial NOT NULL,
    categoria varchar(255) NULL,
    titulo_contabil varchar(255) NULL,
    data_receita date NULL,
    valor_receita float8 NULL,
    descricao varchar(255) NULL,
    conta_interna_id int8 NULL,
    origem varchar(250) NULL,
    banco_origem varchar(250) NULL,
    agencia_origem varchar(250) NULL,
    numero_conta_origem varchar(250) NULL,
    banco_destino varchar(250) NULL,
    agencia_destino varchar(250) NULL,
    numero_conta_destino varchar(250) NULL,
    CONSTRAINT receita_pkey PRIMARY KEY (id),
    CONSTRAINT receita_conta_interna_id_fkey FOREIGN KEY (conta_interna_id) REFERENCES public.conta_interna(id)
);


CREATE TABLE conta_bancaria (
    id serial NOT NULL,
    nome_banco varchar(255) NOT NULL,
    tipo_conta varchar(100) NOT NULL,
    agencia varchar(25) NOT NULL,
    numero_conta varchar(25) NOT NULL
);

INSERT INTO conta_bancaria (nome_banco, tipo_conta, agencia, numero_conta)
VALUES ('Banco do Brasil', 'Corrente', '1234', '12345678'),
       ('Caixa Econômica Federal', 'Poupança', '5678', '87654321'),
       ('Banco Bradesco', 'Corrente', '2365', '63259874'),
       ('Caixa Bradesco', 'Poupança', '1111', '11114321'),
       ('Banco Inter', 'Corrente', '1024', '10245678'),
       ('Banco Next', 'Poupança', '5678', '86325921'),
       ('C6 Bank', 'Corrente', '1263', '12349678'),
       ('Banco Neon', 'Corrente', '3698', '12584321'),
       ('Banco Iti', 'Corrente', '9101', '98700032')
    ('Banco do Brasil', 'Corrente', '0004', '00045678'),
		('Banco do Brasil', 'Corrente', '0214', '02145678'),
		('Banco do Brasil', 'Poupança', '0504', '05045678'),
		('Banco do Brasil', 'Poupança', '1204', '12045678'),
       ('Banco Bradesco', 'Corrente', '2365', '23659874'),
       ('Banco Bradesco', 'Poupança', '0052', '00529874'),
       ('Banco Bradesco', 'Corrente', '2300', '23009874'),
       ('Banco Bradesco', 'Poupança', '2322', '23229874'),
       ('Caixa Bradesco', 'Poupança', '1111', '11114321'),
       ('Banco Inter', 'Corrente', '1024', '10245678'),
       ('Banco Inter', 'Poupança', '1025', '10255678'),
       ('Banco Inter', 'Investimento', '1026', '10265678'),
       ('Banco Inter', 'Investimento', '1027', '10275678');


CREATE TABLE despesa (
     id bigserial NOT NULL,
     categoria varchar(255) NULL,
     titulo_contabil varchar(255) NULL,
     data_despesa date NULL,
     valor_despesa float8 NULL,
     descricao varchar(255) NULL,
     conta_interna_id int8 NULL,
     categoria_transacao varchar(250) NULL,
     tipo_conta_origem varchar(205) NULL,
     banco_origem varchar(250) NULL,
     agencia_origem varchar(250) NULL,
     numero_conta_origem varchar(250) NULL,
     banco_destino varchar(250) NULL,
     agencia_destino varchar(250) NULL,
     numero_conta_destino varchar(250) NULL,
     CONSTRAINT despesa_pkey PRIMARY KEY (id),
     CONSTRAINT despesa_conta_interna_id_fkey FOREIGN KEY (conta_interna_id) REFERENCES public.conta_interna(id)
);

create table categoria_despesa (
    id bigserial NOT NULL PRIMARY KEY,
    nome varchar(255) NULL
);

insert into categoria_despesa (nome)
values ('Despesas Fixas'),
       ('Lazer');

CREATE TABLE titulos_contabeis_despesa (
   id bigserial NOT NULL,
   nome text NULL,
   categoria_despesa_id int4 NULL,
   CONSTRAINT titulos_contabeis_despesa_pkey PRIMARY KEY (id),
   CONSTRAINT titulos_contabeis_despesa_categoria_despesa_id_fkey FOREIGN KEY (categoria_despesa_id) REFERENCES public.categoria_despesa(id)
);

insert into titulos_contabeis_despesa (nome, categoria_despesa_id)
values ('Internet', 1),
       ('Água encanada', 1),
       ('Cosern', 1),
       ('Passeio na Praia', 2),
       ('Passeio no Shopping', 2);
