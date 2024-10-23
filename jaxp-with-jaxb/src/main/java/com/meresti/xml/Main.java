package com.meresti.xml;

import com.meresti.xml.model.Item;
import com.meresti.xml.model.PurchaseOrder;
import com.meresti.xml.model.USAddress;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class Main {
    public static void main(String[] args) throws Exception {
        final Path file = Files.createTempFile("jaxp-jaxb-example-", ".xml");
        final List<PurchaseOrder> purchaseOrders = createPurchaseOrders();
        writeToFile(purchaseOrders, file);
    }

    private static List<PurchaseOrder> createPurchaseOrders() {
        final USAddress joeysAddress = USAddress.builder()
                .street("4376 Paradise Lane")
                .city("Ontario")
                .state("CA")
                .zip(BigDecimal.valueOf(91761L))
                .country("US")
                .build();

        final USAddress monicasAddress = USAddress.builder()
                .street("4215 Calvin Street")
                .city("Baltimore")
                .state("MD")
                .zip(BigDecimal.valueOf(21202L))
                .country("US")
                .build();

        final USAddress phoebesAddress = USAddress.builder()
                .street("2190 Benson Park Drive")
                .city("Oklahoma City")
                .state("OK")
                .zip(BigDecimal.valueOf(73102L))
                .country("US")
                .build();

        final Item sofa = Item.builder()
                .comment("The new fancy sofa")
                .productName("ghost")
                .partNum("1fd954md4")
                .quantity(1)
                .usPrice(new BigDecimal("78.99"))
                .shipDate(LocalDate.now().plusDays(2))
                .build();

        final Item chair = Item.builder()
                .comment("The new Rosita")
                .productName("rosita")
                .partNum("6545ef23gdf2")
                .quantity(1)
                .usPrice(new BigDecimal("32.49"))
                .shipDate(LocalDate.now().plusDays(1))
                .build();

        final Item hairDryer = Item.builder()
                .comment("Wavytalk Professional Hair Dryer")
                .productName("wavytalk")
                .partNum("32ds5434ttds")
                .quantity(1)
                .usPrice(new BigDecimal("37.83"))
                .shipDate(LocalDate.now().plusDays(2))
                .build();

        final PurchaseOrder purchaseOrder1 = PurchaseOrder.builder()
                .orderDate(ZonedDateTime.now().minusDays(7))
                .comment("New sofa and chair")
                .billTo(monicasAddress)
                .shipTo(joeysAddress)
                .items(List.of(sofa, chair))
                .build();

        final PurchaseOrder purchaseOrder2 = PurchaseOrder.builder()
                .orderDate(ZonedDateTime.now().minusDays(3))
                .comment("hairdryer")
                .billTo(monicasAddress)
                .shipTo(phoebesAddress)
                .items(List.of(hairDryer))
                .build();

        return List.of(purchaseOrder1, purchaseOrder2);
    }

    private static void writeToFile(List<PurchaseOrder> purchaseOrders, Path file)
            throws IOException, XMLStreamException, JAXBException {
        final XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        try (final OutputStream outputStream = new FileOutputStream(file.toFile())) {
            final XMLStreamWriter xmlWriter = new IndentingXMLStreamWriter(outputFactory.createXMLStreamWriter(outputStream));
            xmlWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");

            xmlWriter.writeStartElement("orders");

            final JAXBContext jaxbContext = JAXBContext.newInstance(PurchaseOrder.class);
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            for (PurchaseOrder purchaseOrder : purchaseOrders) {
                marshaller.marshal(purchaseOrder, xmlWriter);
            }

            xmlWriter.writeEndElement();

            xmlWriter.writeEndDocument();
            xmlWriter.flush();
            xmlWriter.close();
        }
    }
}