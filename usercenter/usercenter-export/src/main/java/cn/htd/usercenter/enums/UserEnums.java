package cn.htd.usercenter.enums;

public class UserEnums {

    /**
     * 员工类型
     *
     */
    public enum EmployeeType {
        OA_EMP("01", "OA员工"), SUBSTATION_EMP("02", "分站员工");

        private String code;
        private String label;

        EmployeeType(String code, String label) {
            this.code = code;
            this.label = label;
        }

        public static EmployeeType getEnumByCode(String code) {
            if (code != null) {
                for (EmployeeType empType : EmployeeType.values()) {
                    if (code.equals(empType.getCode())) {
                        return empType;
                    }
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * 员工所属公司类型
     *
     */
    public enum EmployeeCompanyType {
        HEADQUARTERS("0", "总部"), BRANCH("1", "分部"), PLATFORM("2", "平台公司");

        private String code;
        private String label;

        EmployeeCompanyType(String code, String label) {
            this.code = code;
            this.label = label;
        }

        public static EmployeeCompanyType getEnumByCode(String code) {
            if (code != null) {
                for (EmployeeCompanyType companyType : EmployeeCompanyType.values()) {
                    if (code.equals(companyType.getCode())) {
                        return companyType;
                    }
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * 员工状态
     *
     */
    public enum EmployeeStatus {
        PROBATION("0", "试用"), REGULAR("1", "正式"), INVALID("7", "无效");

        private String code;
        private String label;

        EmployeeStatus(String code, String label) {
            this.code = code;
            this.label = label;
        }

        public static EmployeeStatus getEnumByCode(String code) {
            if (code != null) {
                for (EmployeeStatus empStatus : EmployeeStatus.values()) {
                    if (code.equals(empStatus.getCode())) {
                        return empStatus;
                    }
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }
    }
}
