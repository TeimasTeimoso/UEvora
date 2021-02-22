/*
Autores: -> Luís Ressonha 35003
         -> Ruben Teimas  39868
*/

--1.
SELECT firstname||' '||lastname AS "Employees Name"
FROM employees;

--2.
SELECT contact_name AS "Nome do Contacto", 
       company_name AS "Nome da Companhia", 
       contact_title AS "Titulo do Contacto", 
       phone AS "Telefone"
FROM customers;

--3.a
SELECT  title AS "Titulo",
        firstname AS "Nome",
        lastname AS "Apelido"
FROM  employees
ORDER BY title;

--3.b
SELECT  title AS "Titulo",
        firstname AS "Nome",
        lastname AS "Apelido"
FROM  employees
ORDER BY lastname desc;

--4.
SELECT *
FROM (SELECT  product_name AS "Nome",
            unit_price AS "Peço Unitario"
      FROM  products
      ORDER BY unit_price;
)
WHERE ROWNUM <= 10;

--5.
SELECT category_name as "Nome da Categoria",
        COUNT(*) AS "Contagem"
FROM categories INNER JOIN products
ON categories.category_id = products.category_id
GROUP BY category_name;

--6.
SELECT product_id AS "Código do Produto",
        product_name AS "Nome do Produto",
        unit_price AS "Preço Unitário"
FROM products
WHERE products.unit_price > (SELECT AVG(unit_price) FROM products);

--7.
SELECT COUNT(CASE WHEN discontinued = 'N' THEN 1 END) AS  "Número de Produtos Atuais",
        COUNT(CASE WHEN discontinued = 'Y' THEN 1 END) AS  "Número de Produtos Descontinuados"
FROM products
WHERE products.discontinued IN ('Y', 'N');

--8.
WITH subtotal AS (SELECT order_id,
                (((1-discount)*unit_price)*quantity) AS "Subtotal"
                FROM order_details)
SELECT TO_CHAR(shipped_date, 'YYYY') AS "Ano",
        shipped_date AS "Data de Envio",
        orders.order_id AS "Número da Encomenda",
        subtotal."Subtotal"
FROM orders INNER JOIN subtotal
ON orders.order_id = subtotal.order_id;

--9.
SELECT products.*,
        categories.category_name
FROM products INNER JOIN categories
ON products.category_id = categories.category_id
WHERE products.discontinued = 'N';

/*10.
Entendemos por estado, se esta descontinuado ou nao*/
SELECT categories.category_name AS "Categoria",
       products.product_name AS "Produto",
       products.quantity_per_unit AS "Quantidade por unidade",
       products.units_in_stock AS "Unidades em stock",
       products.discontinued AS "Estado"
FROM categories INNER JOIN products
ON categories.category_id = products.category_id
WHERE products.discontinued = 'N';
       
--11.
SELECT city AS "Cidade",
        company_name AS "Empresa",
        contact_name AS "Contacto",
        contact_title AS "Relação"
FROM customers
UNION
SELECT city AS "Cidade",
        company_name AS "Empresa",
        contact_name AS "Contacto",
        contact_title AS "Relação"
FROM suppliers
        
--12. FALTA A 12

--13.
SELECT *
FROM(SELECT country
    FROM customers
    GROUP BY country
    ORDER BY COUNT(country) desc)
WHERE ROWNUM = 1;
       
--14.
/* Nao ta certa */
SELECT customers.company_name AS "Nome da Companhia",
        orders.order_id AS "Número da Encomenda",
        (((1-order_details.discount)*order_details.unit_price)*order_details.quantity) AS "Preço Total"
FROM customers INNER JOIN orders ON customers.customer_id = orders.customer_id
INNER JOIN order_details ON orders.order_id = order_details.order_id
WHERE "Preço Total" > 10000;

--15.
SELECT orders.order_id AS "Nº da Encomenda",
        employees.title AS "Cargo",
        employees.firstname || ' ' || employees.lastname AS "Nome",
        territories.territory_description AS "Território",
        region.region_description AS "Região"
FROM orders INNER JOIN employees ON orders.employee_id = employees.employee_id
INNER JOIN employee_territories ON employee_territories.employee_id = employees.employee_id
INNER JOIN territories ON territories.territory_id = employee_territories.territory_id
INNER JOIN region ON territories.region_id = region.region_id
WHERE SUBSTR(territories.territory_description, 0, 1) = 'W';

--16.
SELECT employees.lastname AS "Apelido",
        SUBSTR(employees.lastname,0,1) AS "Primeira Letra",
        SUBSTR(employees.lastname, -1, 1) AS "Última Letra",
        CASE 
            WHEN SUBSTR(employees.lastname,0,1) = 'a' OR SUBSTR(employees.lastname,0,1) = 'e' OR  SUBSTR(employees.lastname,0,1) = 'i' 
            OR SUBSTR(employees.lastname,0,1) = 'o' OR  SUBSTR(employees.lastname,0,1) = 'u'
            THEN 'começa com uma vogal'
            ELSE 'não começa com uma vogal'
        END AS "CASE para a primeira Letra",
        CASE
            WHEN SUBSTR(employees.lastname,-1,1) = 'a' OR SUBSTR(employees.lastname,-1,1) = 'e' OR  SUBSTR(employees.lastname,-1,1) = 'i' 
            OR SUBSTR(employees.lastname,-1,1) = 'o' OR  SUBSTR(employees.lastname,-1,1) = 'u'
            THEN 'termina com uma vogal'
            ELSE 'não termina com uma vogal'
        END AS "CASE para a primeira Letra"
FROM employees;

--17.
SELECT orders.order_date AS "Data da Encomenda",
        orders.shipped_date AS "Data de Envio",
        customers.customer_id AS "Código de Cliente",
        orders.freight AS "Portes"
FROM orders INNER JOIN customers ON orders.customer_id = customers.customer_id
WHERE orders.order_date = '97.05.19';