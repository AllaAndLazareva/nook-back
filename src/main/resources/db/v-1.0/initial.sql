CREATE TABLE document (
                          id bigint NOT NULL,
                          type varchar(255),
                          data bool,
                          nook_id varchar(255),
                          PRIMARY KEY (id),
                          FOREIGN KEY (nook_id) REFERENCES nook.nook(id)
);