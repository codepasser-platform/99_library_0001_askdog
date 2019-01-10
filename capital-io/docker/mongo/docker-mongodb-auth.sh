# docker exec [OPTIONS] CONTAINER COMMAND [ARG...]
docker exec -it mongo-master mongo admin
## execute create user
#db.createUser({ user: 'root', pwd: 'Sa*963.-+', roles: [ { role: "root", db: "admin" } ] });
