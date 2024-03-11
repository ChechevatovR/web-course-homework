create table public.links_github
(
    id                  integer not null
        constraint links_github_pk
            primary key
        constraint links_github_links_id_fk
            references public.links
            on update restrict on delete cascade,
    latest_issue_number integer not null,
    latest_pr_number    integer not null
);
