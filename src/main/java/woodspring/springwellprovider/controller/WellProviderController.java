package woodspring.springwellprovider.controller;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woodspring.springwellprovider.service.ProduceService;

@RestController
@RequestMapping("/kafka")
public class WellProviderController {
	private final static Logger logger = LoggerFactory.getLogger(WellProviderController.class);
	@Autowired
    private ProduceService stringMsgProducer;

    @GetMapping(value = "/msg/{msgBody}")
    public String sendMessage(@PathVariable String msgBody) {
    	//stringMsgProducer.init("KINGSLAKE");
    	logger.info("============================================ msgBody:"+msgBody);
    	String retStr = stringMsgProducer.sendMessage(msgBody);
        return retStr;
    }

    
    @GetMapping(value = "/msgNo/{msgNo}/msg/{msgBody}")
    public String sendMessage(@PathVariable int msgNo, @PathVariable String msgBody) {
    	//stringMsgProducer.init("KINGSLAKE");
    	logger.info("msgNo:"+msgNo+" msgBody:"+msgBody);
    	String retStr = stringMsgProducer.sendMessages(msgBody, msgNo);
        return retStr;
    }
}
