package com.savics.savics.model;

import com.savics.savics.MainApplication;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class User extends RealmObject {

  @PrimaryKey
  private String id = UUID.randomUUID().toString();



  private String lastname;
  private String firstname;
  private int age;
  private String sex;
  private String diabete;
  private String city;
  private String country;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getDiabete() {
    return diabete;
  }

  public void setDiabete(String diabete) {
    this.diabete = diabete;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public static void deleteAll() {
    Realm realm = MainApplication.getRealm();
    RealmResults<User> results = realm.where(User.class).findAll();
    realm.beginTransaction();
    if (results != null && !results.isEmpty()) {
      results.deleteAllFromRealm();
    }
    realm.commitTransaction();
    realm.close();
  }

  public static User findById(long id) {

    User object = null;
    RealmQuery<User> query = MainApplication.getRealm().where(User.class).equalTo("id", id);
    if (query != null) {
      object = query.findFirst();
    }
    return object;
  }

  public static ArrayList<User> findAllOrderedByTitle() {
    ArrayList<User> objects = new ArrayList<>();
    RealmResults<User> results =
        MainApplication.getRealm().where(User.class).findAll().sort("firstname", Sort.ASCENDING);
    if (results != null && results.size() > 0) {
      objects.addAll(results.subList(0, results.size()));

      Collections.copy(objects, results.subList(0, results.size()));
    }
    return objects;
  }

  public static ArrayList<User> findMinors() {
    ArrayList<User> objects = new ArrayList<>();
    RealmResults<User> results =
        MainApplication.getRealm().where(User.class).lessThan("age", 18).findAll().sort("firstname", Sort.ASCENDING);
    if (results != null && results.size() > 0) {
      objects.addAll(results.subList(0, results.size()));

      Collections.copy(objects, results.subList(0, results.size()));
    }
    return objects;
  }

  public static void save(User document) {
    Realm realm = MainApplication.getRealm();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(document);
    realm.commitTransaction();
    realm.close();
  }

  public static void saveAll(ArrayList<User> objects) {
    Realm realm = MainApplication.getRealm();
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(objects);
    realm.commitTransaction();
    realm.close();
  }

  public static ArrayList<User> search(String key) {

    ArrayList<User> objects = new ArrayList<>();
    RealmResults<User> results = MainApplication.getRealm().where(User.class)
        .like("title", "*" + key + "*")
        .or().like("title", "*" + key.toUpperCase() + "*")
        .or().like("title", "*" + key.toLowerCase() + "*")
        .findAll().sort("title", Sort.ASCENDING);
    if (results != null && results.size() > 0) {
      objects.addAll(results.subList(0, results.size()));
      Collections.copy(objects, results.subList(0, results.size()));
    }
    return objects;
  }

  @Override public String toString() {
    return getFirstname()+" "+getLastname()+" ("+getSex()+"), "+getAge()+" - "+getCity()+"("+getCountry()+")";
  }
}
