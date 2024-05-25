CREATE TABLE projetos (
      id serial4 NOT NULL,
      nome text NOT NULL,
      categoria text NOT NULL,
      valor_meta text NOT NULL,
      data_inicio date NOT NULL,
      data_final date NOT NULL,
      descricao text NOT NULL,
      valor_arrecadado_atual text NOT NULL,
      CONSTRAINT projetos_pkey PRIMARY KEY (id)
);

create table balancos (
      id serial4 NOT NULL,
      nome text NOT NULL,
      tipoBalanco text NOT NULL,
      analise_balanco text NOT NULL,
      data_inicio date NOT NULL,
      data_final date NOT NULL,
      tipo_visualizacao text NOT NULL,
      categoria_titulo_contabil text NOT NULL,
      CONSTRAINT balancos_pkey PRIMARY KEY (id)
)

create table dash (
  id serial4 NOT NULL,
  identicador_balanco bigint NOT NULL
)