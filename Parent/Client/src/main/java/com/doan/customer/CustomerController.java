package com.doan.customer;

import com.doan.Utility;
import com.doan.mail.Mail;
import com.doan.mail.MailService;
import com.doan.mutual.entity.City;
import com.doan.mutual.entity.Customer;
import com.doan.security.CustomerUserDetails;
import com.doan.security.oauth.CustomerOAuth2User;
import com.doan.setting.EmailSettingBag;
import com.doan.setting.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

@Controller
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private SettingService settingService;

    @Autowired
    MailService mailService;
    @Autowired
    HttpSession session;
    @GetMapping("/register")
    public String registerForm(Model model){
        List<City> listCity = customerService.listAllCity();
        model.addAttribute("listCity", listCity);
        model.addAttribute("pageTitle", "Customer Registration");
        model.addAttribute("customer", new Customer());

        return "register/sign-up";

    }

    @PostMapping("/create_customer")
    public String createCustomer(@ModelAttribute("customer") Customer customer,HttpServletRequest request, @Param("email") String email, Model model) throws UnsupportedEncodingException, MessagingException{
        if (!customerService.isEmailUnique(email)){
            model.addAttribute("errorSignUp", "Tài khoản đã tồn tại!");
            return "register/sign-up";
        }
        customerService.saveRegister(customer);
        sendVerificationEmail(request, customer);
        session.setAttribute("register","createRegisterSuccess");
        return "redirect:/login";
    }
    private void sendVerificationEmail(HttpServletRequest request, Customer customer)
            throws UnsupportedEncodingException, MessagingException {
        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);

        String toAddress = customer.getEmail();
        String subject = emailSettings.getCustomerVerifySubject();
        String content = emailSettings.getCustomerVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", customer.getFullName());

        String verifyURL = Utility.getSiteURL(request) + "/verify?code=" + customer.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("to Address: " + toAddress);
        System.out.println("Verify URL: " + verifyURL);
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model model) {
        boolean verified = customerService.verify(code);

        return "register/" + (verified ? "verify_success" : "verify_fail");
    }
    private void updateNameForAuthenticatedCustomer(Customer customer, HttpServletRequest request) {
        Object principal = request.getUserPrincipal();

        if (principal instanceof UsernamePasswordAuthenticationToken
                || principal instanceof RememberMeAuthenticationToken) {
            CustomerUserDetails userDetails = getCustomerUserDetailsObject(principal);
            Customer authenticatedCustomer = userDetails.getCustomer();
            authenticatedCustomer.setFirstName(customer.getFirstName());
            authenticatedCustomer.setLastName(customer.getLastName());

        } else if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) principal;
            CustomerOAuth2User oauth2User = (CustomerOAuth2User) oauth2Token.getPrincipal();
            String fullName = customer.getFirstName() + " " + customer.getLastName();
            oauth2User.setFullName(fullName);
        }
    }

    private CustomerUserDetails getCustomerUserDetailsObject(Object principal) {
        CustomerUserDetails userDetails = null;
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
            userDetails = (CustomerUserDetails) token.getPrincipal();
        } else if (principal instanceof RememberMeAuthenticationToken) {
            RememberMeAuthenticationToken token = (RememberMeAuthenticationToken) principal;
            userDetails = (CustomerUserDetails) token.getPrincipal();
        }

        return userDetails;
    }

    @PostMapping("/check_unique_email")
    public String checkDuplicateEmail(@Param("email") String email) {
        return customerService.isEmailUnique(email) ? "OK" : "Duplicated";
    }

    @GetMapping("/forgot")
    public String forGotView(Model model) {
        String error_forgot = (String) session.getAttribute("error_forgot");
        model.addAttribute("error_forgot", error_forgot);
        session.setAttribute("error_forgot", null);
        model.addAttribute("forgot", "Forgot Password");
        return "login";
    }

    @PostMapping("/forgot")
    public String forGotHandel(@ModelAttribute("login-name") String login_name, Model model) throws Exception {
        Customer user = customerService.getCustomerByEmail(login_name);
        if (user == null) {
            session.setAttribute("error_forgot", "UserName is not correct!");
            return "redirect:/forgot";
        } else {
            session.setAttribute("userForgot", user);
            return "redirect:/code";
        }
    }

    @GetMapping("/code")
    public String codeView(Model model) throws Exception {
        Customer userForgot = (Customer) session.getAttribute("userForgot");
        String noSendEmail = (String) session.getAttribute("noSendEmail");
        if (noSendEmail == null) {
            int code = (int) Math.floor(((Math.random() * 899999) + 100000));
            Mail mail = new Mail();
            mail.setMailFrom("trangdc.103@gmail.com");
            mail.setMailTo(userForgot.getEmail());
            mail.setMailSubject("For got Password");
            mail.setMailContent("Your code is: " + code);
            mailService.sendEmail(mail);
            System.out.println(code);
            session.setAttribute("code", code);
        }
        session.setAttribute("noSendEmail", null);
        String error_code = (String) session.getAttribute("error_code");
        model.addAttribute("error_code", error_code);
        session.setAttribute("error_code", null);
        model.addAttribute("forgot", "Forgot Password");
        model.addAttribute("sendcode", "sendcode");
        return "login";
    }

    @PostMapping("/code")
    public String codeHandel(@ModelAttribute("code_input") int code_input, Model model) throws Exception {
        int code = (int) session.getAttribute("code");
        if (code == code_input) {
            session.setAttribute("code", null);
            return "redirect:/newpass";
        } else {
            session.setAttribute("noSendEmail", "noSendEmail");
            session.setAttribute("error_code", "Code is not correct!");
            return "redirect:/code";
        }

    }

    @GetMapping("/newpass")
    public String newPassView(Model model) {
        String error_newpass = (String) session.getAttribute("error_newpass");
        session.setAttribute("error_newpass", null);
        model.addAttribute("error_newpass", error_newpass);
        model.addAttribute("forgot", "Forgot Password");
        model.addAttribute("sendcode", "sendcode");
        model.addAttribute("changepass", "changepass");
        return "login";
    }

    @PostMapping("newpass")
    public String newPassHandel(@ModelAttribute("new_pass") String new_pass,
                                @ModelAttribute("confirm_new_pass") String confirm_new_pass, Model model) throws Exception {
        if (new_pass.equals(confirm_new_pass)) {
            Customer userForgot = (Customer) session.getAttribute("userForgot");
            customerService.savePass(userForgot,  new_pass);
            session.setAttribute("resetpass","resetpassSuccess");
            return "redirect:/login";
        } else {
            session.setAttribute("error_newpass", "Confirm New Password not valid!");
            return "redirect:/newpass";
        }

    }
}
