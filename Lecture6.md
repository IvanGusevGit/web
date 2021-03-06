# Spring Framework

**Spring Boot** - framework for configuration of spring framework
All configurations are provided by standard and it is better to use it as it is and do not try to change this. This configurations are well organised and very suitable and efficient for usage.

Spring controls the lifetime of huge amount functional objects. As a result we won't code `new` many times. We won't create many instances. All them will be pushed from outside and will be managed by spring. Sometimes we will create dependencies by marking constructors is specific way.

## Useful links

- spring boot (spring.io)
- start.spring.io - project initializer

## Dependencies
- jps
- freemarker
- jpa - interface for to standard work with databases
- MySQL
- DevTools

`@Controller` - create only one instance. Lifetime is controlled by spring. It is marked as component which contains methods and actions.

```
@Controller
public class IndexPage {
	@GetMapping(path = "") executed on "/"
	public String main(Model model) {
		model.addAttrinbute("name", "mike");
		return "IndexPage"; //returns rendrering to index page
	}
}
```

Urls are automatically parsed to actions 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTQyNTczNjgzMywtMTc4ODAzMTUzNSwzNj
E5MTk4MjUsMTkzNTc4MTgwMywtMTYzNDg0Njk2MCw0MTk2OTg0
MDAsLTIwODg3NDY2MTJdfQ==
-->