package org.example.vo;



import java.util.List;

    public class VendorVO {

        private String firstName;
        private String email;
        private List<AddressVO> addresses;

        public List<AddressVO> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<AddressVO> addresses) {
            this.addresses = addresses;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public static class AddressVO {


            private String streetName;
            private String state;
            private int pinCode;




            public String getStreetName() {
                return streetName;
            }

            public void setStreetName(String streetName) {
                this.streetName = streetName;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public int getPinCode() {
                return pinCode;
            }

            public void setPinCode(int pinCode) {
                this.pinCode = pinCode;
            }
        }

}
