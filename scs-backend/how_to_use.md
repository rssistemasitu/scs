###1. Criar o banco de dados utilizando o Mongosh

```
use scs
```
###2. Criar as ROLES

```
db.roles.insertMany([
{ name: "ROLE_USER" },
{ name: "ROLE_DEPENDENT" },
{ name: "ROLE_MODERATOR" },
{ name: "ROLE_ADMIN" },
])
```

###3. Criar um usuário ADMIN 
a senha será: ```mHGeRAkxyeeLkQ77DEQCzEMP5y1r4```

```
db.users.insertOne(
{
"email": "seu_email@teste.com", 
"password": "$2a$10$e.dMMJYYbTwvrn0lhq.UiOacie0wZHEVnH6NP2LS.tu6H8tWJZ.Xm",  
"status":true,
"roles": [ {"$ref": "roles", "$id": ObjectId("ID_DO_ADMIN_CRIADO_ACIMA")}]})
```


