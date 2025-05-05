## Format String with readable text
```java
String formattedPermissions_2 = permissions.subList(permissions.size() / 2, permissions.size()).stream()
                                           .map(Permission::getName) // Get the readable name of each permission
                                           .collect(Collectors.joining(", "));
```
### Output
```
View channels, Manage nicknames, Administrator, ...
```