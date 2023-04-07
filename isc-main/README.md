=====================================
CORS and dev-ui profile thoughts:
=====================================
1. Work in production
we will do production build of react app
all files will be copied 2 places: inside of spring boot and inside of static container. One build == one set of generated files
Nginx server will route traffic to either REST (+index and login pages) or everything under /static/static/** -> static container
I.e. no need to route to static content inside of Spring Boot -> no need in dev-ui

2. Work in local dev environment
Run separate react (development) and spring boot
Configure proxy in react app to make http calls to rest -> no need for routing static resources in spring boot, no need in dev-ui profile.

Summary:
- In both cases no need CORS. In first case, because all calls to the same host/port, in the second case proxying will be done by 
node.js server on server side ... UI doesn't know that it is calling to other port.
- In both cases no need in Spring Boot to manage static resources' context path
=====================================
