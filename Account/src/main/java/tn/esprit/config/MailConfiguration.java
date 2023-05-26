package tn.esprit.config;



//@Configuration
//public class MailConfiguration {
//
//	@Autowired
//    private Environment env;
// 
//    public JavaMailSender getMailSender(CompteMailDto compteMail) {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
// 
//        mailSender.setHost(env.getProperty("spring.mail.host"));
//        mailSender.setPort(Integer.valueOf(env.getProperty("spring.mail.port")));
//        mailSender.setUsername(env.getProperty(compteMail.getEmail()));
//        mailSender.setPassword(env.getProperty(compteMail.getPassword()));
// 
//        Properties javaMailProperties = new Properties();
//        javaMailProperties.put("mail.smtp.starttls.enable", "true");
//        javaMailProperties.put("mail.smtp.auth", "true");
//        javaMailProperties.put("mail.transport.protocol", "smtp");
//        javaMailProperties.put("mail.debug", "true");
// 
//        mailSender.setJavaMailProperties(javaMailProperties);
//        return mailSender;
//    }
//}
