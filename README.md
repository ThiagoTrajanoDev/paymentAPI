## Simplified payment API
That's a simple payment API made with Spring Boot, JPA and H2 data base.

### What it can do?
- Register users
  - Users have unique email and document ID
  - Password (the API does not have autentication yet)
  - First and last name
  - A balance
  - A user type (Merchant  and Common)
- Send transactions to an account to another
- Merchants can only recieve transactions
- The transaction can't be greater than the balance value

#### Where the ideia came from?
This was inspired by an coding interview, the repo came public after the process and i did as a challenge <br>
You can check [here](https://github.com/PicPay/picpay-desafio-backend)
