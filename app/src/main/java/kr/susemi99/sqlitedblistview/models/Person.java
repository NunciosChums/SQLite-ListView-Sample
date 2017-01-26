package kr.susemi99.sqlitedblistview.models;

public class Person {
  public String id;
  public String name;
  public String created_at;
  public String updated_at;

  public Person(String id, String name, String created_at, String updated_at) {
    super();
    this.id = id;
    this.name = name;
    this.created_at = created_at;
    this.updated_at = updated_at;
  }

  public Person(String name, String created_at, String updated_at) {
    super();
    this.name = name;
    this.created_at = created_at;
    this.updated_at = updated_at;
  }
}
