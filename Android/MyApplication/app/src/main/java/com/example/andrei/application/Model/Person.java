package com.example.andrei.application.Model;


    public class Person {
        private int PersonID;
        private String FirstName;
        private String LastName;
        private String Email;
        private int Age;
        private String Phone;
        private String Country;
        private int UserID;
        public Person(int PersonID,String FN,String LN,String Email,int Age,String Phone,String Country){
            this.PersonID=PersonID;
            this.FirstName=FN;
            this.LastName=LN;
            this.Email=Email;
            this.Age=Age;
            this.Phone=Phone;
            this.Country=Country;
        }
        public int getID(){return PersonID;}
        public String getFirstName(){
            return FirstName;
        }
        public void setFirstName(String value){
            this.FirstName=value;
        }
        public String getLastName(){
            return LastName;
        }
        public void setLastName(String value){
            this.LastName=value;
        }
        public String getEmail(){
            return Email;
        }
        public void setEmail(String value){this.Email=value;}
        public int getAge(){
            return Age;
        }
        public void setAge(int value){
            this.Age=value;
        }
        public String getPhone(){
            return Phone;
        }
        public void setPhone(String value){this.Phone=value;}
        public String getCountry(){
            return Country;
        }
        public void setCountry(String value){this.Country=value;}
        @Override
        public String toString(){
            return FirstName+" "+LastName;
        }
    }
