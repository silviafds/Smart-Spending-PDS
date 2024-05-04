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
