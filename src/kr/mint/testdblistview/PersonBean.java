package kr.mint.testdblistview;

public class PersonBean
{
  public String id;
  public String name;
  public String created_at;
  public String updated_at;
  
  
  public PersonBean(String $id, String $name, String $created_at, String $updated_at)
  {
    super();
    id = $id;
    name = $name;
    created_at = $created_at;
    updated_at = $updated_at;
  }
  
  
  public PersonBean(String $name, String $created_at, String $updated_at)
  {
    super();
    name = $name;
    created_at = $created_at;
    updated_at = $updated_at;
  }
}
