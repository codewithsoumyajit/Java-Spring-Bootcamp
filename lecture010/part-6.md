# PART 6 — SECURITY & REAL WORLD

# 1. Why Password Should Not Use `String`

One of the most important real-world Java interview questions.

Most beginners write:

```java
String password = "admin123";
```

Looks fine.

But internally this is dangerous.

---

# Problem with String Passwords

`String` in Java is:

* Immutable
* Stored in String Pool (sometimes)
* Cannot be modified
* Stays in memory until Garbage Collector removes it

That means:

```java
String password = "secret";
```

The password may remain in memory for a long time.

A hacker using:

* memory dump
* heap dump
* debugger
* malware

can potentially read it.

---

# Better Approach → `char[]`

```java
char[] password = {'s','e','c','r','e','t'};
```

Why safer?

Because after usage:

```java
Arrays.fill(password, '*');
```

You can manually erase it from memory.

---

# Real World Example

## Login Form

Bad:

```java
String pwd = passwordField.getText();
```

Better:

```java
char[] pwd = passwordField.getPassword();
```

That’s why Java security APIs prefer `char[]`.

---

# Internal Reason

`String`:

```java
final class String
```

Immutable.

Cannot change internal value.

But array is mutable:

```java
char[] arr
```

can be overwritten.

---

# Interview Question

## Q: Why is `char[]` preferred over `String` for passwords?

Answer:

* String is immutable
* stays in memory longer
* cannot be cleared manually
* vulnerable in memory dump
* `char[]` can be wiped after usage

---

# 2. Security Risks with String

# A. Hardcoded Passwords

BAD:

```java
String dbPassword = "root123";
```

Very dangerous.

If pushed to GitHub accidentally:

* database hacked
* API compromised
* cloud account stolen

---

# Better

Use:

* environment variables
* config vaults
* secret managers

Example:

```java
System.getenv("DB_PASSWORD");
```

---

# B. Logging Sensitive Data

BAD:

```java
System.out.println(password);
```

Now password enters:

* logs
* monitoring tools
* cloud systems

Huge security issue.

---

# C. String Concatenation in Queries

BAD:

```java
String query =
    "SELECT * FROM users WHERE name='"
    + username + "'";
```

This creates SQL Injection risk.

---

# 3. SQL Injection Basics

One of the most dangerous real-world attacks.

---

# Vulnerable Code

```java
String query =
    "SELECT * FROM users WHERE username='"
    + userInput + "'";
```

If attacker enters:

```sql
' OR '1'='1
```

Final query becomes:

```sql
SELECT * FROM users
WHERE username='' OR '1'='1'
```

Condition always true.

Database hacked.

---

# Solution → PreparedStatement

```java
String query =
    "SELECT * FROM users WHERE username=?";

PreparedStatement ps =
    conn.prepareStatement(query);

ps.setString(1, userInput);
```

Now SQL engine treats input as DATA not CODE.

---

# Real World Impact

SQL Injection can:

* bypass login
* delete database
* steal user info
* leak passwords
* hack payment systems

---

# 4. String in APIs

APIs mostly communicate using Strings.

Example HTTP Request:

```http
GET /users/10
```

Everything transferred as text.

---

# JSON APIs

Client sends:

```json
{
  "name": "Soumyajit",
  "email": "abc@gmail.com"
}
```

Server receives it as String data first.

Then converts to objects.

---

# Spring Boot Example

```java
@PostMapping("/user")
public void createUser(
    @RequestBody User user
) {

}
```

Internally:

1. JSON String arrives
2. Jackson parser converts
3. Object created

---

# Why String Matters in APIs

APIs constantly:

* parse strings
* validate strings
* sanitize strings
* encode/decode strings

---

# Common API Problems

## A. Invalid Input

```json
{
  "age": "hello"
}
```

Expected number.

Got String.

Parsing error.

---

## B. Huge Strings

Attacker sends:

* 100MB JSON
* long payloads

Can crash server.

---

## C. Unsafe Characters

Special characters:

* `<script>`
* SQL commands
* escape sequences

can become attack vectors.

---

# 5. String in JSON/XML

# JSON

Modern APIs mostly use JSON.

Example:

```json
{
  "product": "Laptop",
  "price": 70000
}
```

JSON is text-based.

Internally all keys are Strings.

---

# XML

Older enterprise systems still use XML.

Example:

```xml
<user>
   <name>Soumyajit</name>
</user>
```

Also text-based.

---

# Problem → Injection Attacks

Unsafe XML can cause:

* XXE attacks
* parser vulnerabilities

Unsafe JSON can cause:

* script injection
* broken parsing

---

# Escaping Special Characters

Suppose:

```java
String name = "John \"Danger\"";
```

JSON must escape quotes:

```json
{
  "name": "John \"Danger\""
}
```

---

# Libraries Handle This

Use:

* Jackson
* Gson
* JAXB

Never manually build JSON/XML strings.

BAD:

```java
String json =
"{\"name\":\"" + name + "\"}";
```

---

# 6. Thread Safety

# Is String Thread Safe?

YES.

Because String is immutable.

---

# Example

```java
String s = "Hello";
```

Two threads can safely read it simultaneously.

No corruption.

---

# Why?

Since String cannot change:

```java
s.concat("World");
```

creates NEW object.

Old object unchanged.

---

# Mutable Objects Are Dangerous

Example:

```java
StringBuilder sb =
    new StringBuilder("Hi");
```

Multiple threads modifying this simultaneously can corrupt data.

---

# StringBuilder vs StringBuffer

## StringBuilder

* Faster
* NOT thread safe

## StringBuffer

* Thread safe
* Synchronized
* Slower

---

# Real World Rule

| Situation                  | Use           |
| -------------------------- | ------------- |
| Single thread              | StringBuilder |
| Multi-thread shared object | StringBuffer  |
| Read-only text             | String        |

---

# 7. Serialization

Serialization means:

> converting object into bytes

Used for:

* saving files
* sending over network
* caching
* distributed systems

---

# Example

```java
class User implements Serializable {
    String name;
}
```

Java converts object into byte stream.

---

# String Serialization

String itself is Serializable.

```java
String msg = "Hello";
```

can directly travel over:

* sockets
* files
* APIs

---

# Problem with Serialization

If sensitive Strings are serialized:

* passwords
* tokens
* API keys

they may leak.

---

# Example

```java
class User {
    String password;
}
```

Serialized object may expose password.

---

# Solution

Use:

```java
transient String password;
```

Now password won’t serialize.

---

# 8. Network Transmission

Whenever data moves:

* browser → server
* app → database
* microservice → microservice

Strings are everywhere.

---

# Example Flow

```text
User Types Text
      ↓
HTTP Request
      ↓
JSON String
      ↓
Server Parsing
      ↓
Database Query
      ↓
Response String
```

Entire internet runs heavily on Strings.

---

# Risks During Transmission

# A. Data Interception

Without HTTPS:

* passwords visible
* tokens visible
* user data visible

---

# B. Encoding Issues

Different systems use:

* UTF-8
* UTF-16
* ASCII

Wrong encoding causes:

* corrupted text
* broken emojis
* data mismatch

---

# Example

```java
byte[] data =
    text.getBytes(StandardCharsets.UTF_8);
```

Always specify charset.

---

# C. Large Payloads

Huge Strings over network:

* increase latency
* consume memory
* slow APIs

---

# Compression

Servers often compress Strings using:

* GZIP
* Brotli

to reduce network usage.

---

# Real World Technologies Using Strings

| Technology | Uses String For       |
| ---------- | --------------------- |
| HTTP       | Requests/Responses    |
| REST APIs  | JSON                  |
| GraphQL    | Queries               |
| Databases  | SQL                   |
| JWT        | Authentication Tokens |
| HTML       | Web Pages             |
| XML        | Enterprise Data       |
| Kafka      | Message Transfer      |
| Redis      | Cached Data           |

---

# Final Deep Dive Summary

| Topic         | Key Idea                   |
| ------------- | -------------------------- |
| Passwords     | Prefer `char[]`            |
| Security      | Avoid hardcoded secrets    |
| SQL Injection | Never concatenate queries  |
| APIs          | Strings carry requests     |
| JSON/XML      | Text-based communication   |
| Thread Safety | String immutable = safe    |
| Serialization | Sensitive Strings can leak |
| Network       | Strings travel everywhere  |

---

# Golden Real-World Rules

## Rule 1

Never trust user input.

---

## Rule 2

Never build SQL manually.

---

## Rule 3

Never hardcode secrets.

---

## Rule 4

Always validate Strings.

---

## Rule 5

Always specify encoding.

---

## Rule 6

Use immutable objects carefully.

---

## Rule 7

Understand:

> Most cyber attacks start with unsafe String handling.
