import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DataBasepassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
}
