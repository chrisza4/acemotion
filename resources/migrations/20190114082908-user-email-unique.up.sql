CREATE UNIQUE INDEX idx_email_unique
    ON public.users USING btree
    (email ASC NULLS LAST)
    TABLESPACE pg_default;
