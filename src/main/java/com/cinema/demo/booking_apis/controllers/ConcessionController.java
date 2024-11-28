//package com.cinema.demo.booking_apis.controllers;
//
//import com.cinema.demo.booking_apis.dtos.ConcessionOrderDTO;
//import com.cinema.demo.booking_apis.services.InvoiceService;
//import com.cinema.demo.entity.ConcessionOrderEntity;
//import com.cinema.demo.entity.InvoiceEntity;
//import com.cinema.demo.entity.TypeOfConcessionEntity;
//import com.cinema.demo.booking_apis.services.ConcessionOrderService;
//import com.cinema.demo.booking_apis.services.TypeOfConcessionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/api/concessions")
//public class ConcessionController {
//
//    @Autowired
//    private TypeOfConcessionService typeOfConcessionService;
//
//    @Autowired
//    private ConcessionOrderService concessionOrderService;
//
//    @Autowired
//    private InvoiceService invoiceService;
//
//    @GetMapping("/food")
//    public String showFoodPage(Model model) {
//        List<TypeOfConcessionEntity> foodList = typeOfConcessionService.getAllConcessions();
//        if (foodList.isEmpty()) {
//            System.out.println("Food list is empty!"); // Thêm dòng này để kiểm tra
//        } else {
//            System.out.println("Food list size: " + foodList.size()); // In ra số lượng món ăn
//        }
//        model.addAttribute("foodList", foodList);
//        return "boleto/demo/popcorn";
//    }
//
//
//    // Xử lý đơn hàng và hiển thị giỏ hàng
//    @PostMapping("/order")
//    public String orderConcession(@RequestParam("concessionTypeId") int concessionTypeId,
//                                  @RequestParam("quantity") int quantity,
//                                  @RequestParam("invoiceId") int invoiceId,
//                                  Model model) {
//        // Lấy InvoiceEntity từ InvoiceService
//        Optional<InvoiceEntity> invoiceEntityOpt = invoiceService.getInvoiceById(invoiceId);
//        if (invoiceEntityOpt.isEmpty()) {
//            model.addAttribute("error", "Invoice không tồn tại");
//            return "boleto/demo/popcorn"; // Quay lại trang food với thông báo lỗi
//        }
//
//        InvoiceEntity invoiceEntity = invoiceEntityOpt.get();
//
//        // Lấy TypeOfConcessionEntity từ ID món ăn
//        Optional<TypeOfConcessionEntity> concessionTypeOpt = typeOfConcessionService.getAllConcessions().stream()
//                .filter(type -> type.getConcessionTypeId() == concessionTypeId)
//                .findFirst();
//
//        if (concessionTypeOpt.isEmpty()) {
//            model.addAttribute("error", "Loại đồ ăn không tồn tại");
//            return "boleto/demo/popcorn";
//        }
//
//        TypeOfConcessionEntity concessionType = concessionTypeOpt.get();
//
//        // Tạo mới đơn hàng và lưu vào CSDL
//        ConcessionOrderEntity concessionOrderEntity = new ConcessionOrderEntity();
//        concessionOrderEntity.setQuantity(quantity);
//        concessionOrderEntity.setPrice(concessionType.getPrice());
//        concessionOrderEntity.setConcessionType(concessionType);
//        concessionOrderEntity.setInvoice(invoiceEntity);
//
//        concessionOrderService.saveConcessionOrder(concessionOrderEntity);
//
//        // Thêm vào giỏ hàng (session hoặc cơ sở dữ liệu)
//        List<ConcessionOrderEntity> cart = (List<ConcessionOrderEntity>) model.getAttribute("cart");
//        if (cart == null) {
//            cart = new ArrayList<>();
//        }
//
//        cart.add(concessionOrderEntity);
//
//        // Tính tổng tiền
//        BigDecimal totalAmount = cart.stream()
//                .map(order -> order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        BigDecimal totalPriceWithVAT = totalAmount.add(BigDecimal.valueOf(15)); // Ví dụ thêm phí VAT 15
//
//        // Hiển thị thông tin giỏ hàng và tổng tiền
//        model.addAttribute("invoiceId", invoiceId);
//        model.addAttribute("cart", cart);
//        model.addAttribute("totalAmount", totalAmount);
//        model.addAttribute("totalPriceWithVAT", totalPriceWithVAT);
//        model.addAttribute("success", "Đặt hàng thành công");
//        return "boleto/demo/popcorn"; // Chuyển tới trang giỏ hàng với thông tin
//    }
//}
