CREATE TABLE public.catalog_service_model(
  id                SERIAL PRIMARY KEY,
  name              VARCHAR(250) NOT NULL,
  id_model           VARCHAR(10) NOT NULL,
  id_vehicle         VARCHAR(10) NOT NULL
);

CREATE TABLE public.quote(
  id                SERIAL PRIMARY KEY,
  full_name              VARCHAR(250) NOT NULL,
  model           VARCHAR(100) NOT NULL,
  version         VARCHAR(100) NOT NULL,
  purchase_id         VARCHAR(100) NOT NULL,
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  cryptocurrency         VARCHAR(100) NOT NULL,
  price_usd NUMERIC(12, 2) DEFAULT 0.0,
  price_cryptocurrency NUMERIC(12, 4) DEFAULT 0.0
);