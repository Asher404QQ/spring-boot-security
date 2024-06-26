insert into spring.users(username, password, enabled) values
      ('user', '{bcrypt}$2y$10$xwqBb7JXvXOKsTZLVd7xl.FwrfEci3P.advxgasXOXHVmhphWRcaO', 1),
      ('admin', '{bcrypt}$2y$10$/sivWlHBlJ6uWnGlwMUsEODAYlDh5.ZcJcPSgbRgTP4ErcxC6V0yK', 1),
      ('disabled', '{bcrypt}$2y$10$xwqBb7JXvXOKsTZLVd7xl.FwrfEci3P.advxgasXOXHVmhphWRcaO', 0);

insert into spring.authorities(username, authority) VALUES
        ('user', 'user'),
        ('admin', 'user'),
        ('admin', 'admin'),
        ('disabled', 'disabled')