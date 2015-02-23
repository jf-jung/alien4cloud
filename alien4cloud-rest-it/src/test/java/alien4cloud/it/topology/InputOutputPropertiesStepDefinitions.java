package alien4cloud.it.topology;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.junit.Assert;

import alien4cloud.it.Context;
import alien4cloud.model.components.PropertyDefinition;
import alien4cloud.rest.topology.TopologyDTO;
import alien4cloud.rest.utils.JsonUtil;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class InputOutputPropertiesStepDefinitions {

    private String getPropertyUrl(String propertyName, String nodeName, String propertyType) {
        return "/rest/topologies/" + Context.getInstance().getTopologyId() + "/nodetemplates/" + nodeName + "/property/" + propertyName + "/" + propertyType;
    }

    private String getAttributesUrl(String attributeName, String nodeName, String inputOrOutput) {
        return "/rest/topologies/" + Context.getInstance().getTopologyId() + "/nodetemplates/" + nodeName + "/attributes/" + attributeName + "/"
                + inputOrOutput;
    }

    @When("^I define the property \"([^\"]*)\" of the node \"([^\"]*)\" as input property$")
    public void I_define_the_property_of_the_node_as_input_property(String propertyName, String nodeName) throws Throwable {
        Context.getInstance().registerRestResponse(
                Context.getRestClientInstance().postUrlEncoded(getPropertyUrl(propertyName, nodeName, "isInput"), new ArrayList<NameValuePair>()));
    }

    @Then("^The topology should have the property \"([^\"]*)\" of the node \"([^\"]*)\" defined as input property$")
    public void The_topology_should_have_the_property_of_the_node_defined_as_input_property(String propertyName, String nodeName) throws Throwable {
        TopologyDTO topologyDTO = JsonUtil.read(Context.getRestClientInstance().get("/rest/topologies/" + Context.getInstance().getTopologyId()),
                TopologyDTO.class).getData();
        Map<String, PropertyDefinition> inputProperties = topologyDTO.getTopology().getInputs();
        Assert.assertNotNull(inputProperties);
        PropertyDefinition inputPropertiesOfNode = inputProperties.get(nodeName);
        Assert.assertNotNull(inputPropertiesOfNode);
        Assert.assertTrue(inputPropertiesOfNode.getType().contains(propertyName));
    }

    @When("^I define the property \"([^\"]*)\" of the node \"([^\"]*)\" as non input property$")
    public void I_define_the_property_of_the_node_as_non_input_property(String propertyName, String nodeName) throws Throwable {
        Context.getInstance().registerRestResponse(Context.getRestClientInstance().delete(getPropertyUrl(propertyName, nodeName, "isInput")));
    }

    @Then("^The topology should not have the property \"([^\"]*)\" of the node \"([^\"]*)\" defined as input property$")
    public void The_topology_should_not_have_the_property_of_the_node_defined_as_input_property(String propertyName, String nodeName) throws Throwable {
        TopologyDTO topologyDTO = JsonUtil.read(Context.getRestClientInstance().get("/rest/topologies/" + Context.getInstance().getTopologyId()),
                TopologyDTO.class).getData();
        Map<String, PropertyDefinition> inputProperties = topologyDTO.getTopology().getInputs();
        if (inputProperties != null) {
            PropertyDefinition inputPropertiesOfNode = inputProperties.get(nodeName);
            if (inputPropertiesOfNode != null) {
                Assert.assertFalse(inputPropertiesOfNode.getType().contains(propertyName));
            }
        }
    }

    @When("^I define the property \"([^\"]*)\" of the node \"([^\"]*)\" as output property$")
    public void I_define_the_property_of_the_node_as_output_property(String propertyName, String nodeName) throws Throwable {
        Context.getInstance().registerRestResponse(
                Context.getRestClientInstance().postUrlEncoded(getPropertyUrl(propertyName, nodeName, "isOutput"), new ArrayList<NameValuePair>()));
    }

    @Then("^The topology should have the property \"([^\"]*)\" of the node \"([^\"]*)\" defined as output property$")
    public void The_topology_should_have_the_property_of_the_node_defined_as_output_property(String propertyName, String nodeName) throws Throwable {
        TopologyDTO topologyDTO = JsonUtil.read(Context.getRestClientInstance().get("/rest/topologies/" + Context.getInstance().getTopologyId()),
                TopologyDTO.class).getData();
        Map<String, Set<String>> outputProperties = topologyDTO.getTopology().getOutputProperties();
        Assert.assertNotNull(outputProperties);
        Set<String> outputPropertiesOfNode = outputProperties.get(nodeName);
        Assert.assertNotNull(outputPropertiesOfNode);
        Assert.assertTrue(outputPropertiesOfNode.contains(propertyName));
    }

    @When("^I define the property \"([^\"]*)\" of the node \"([^\"]*)\" as non output property$")
    public void I_define_the_property_of_the_node_as_non_output_property(String propertyName, String nodeName) throws Throwable {
        Context.getInstance().registerRestResponse(Context.getRestClientInstance().delete(getPropertyUrl(propertyName, nodeName, "isOutput")));
    }

    @Then("^The topology should not have the property \"([^\"]*)\" of the node \"([^\"]*)\" defined as output property$")
    public void The_topology_should_not_have_the_property_of_the_node_defined_as_output_property(String propertyName, String nodeName) throws Throwable {
        TopologyDTO topologyDTO = JsonUtil.read(Context.getRestClientInstance().get("/rest/topologies/" + Context.getInstance().getTopologyId()),
                TopologyDTO.class).getData();
        Map<String, Set<String>> outputProperties = topologyDTO.getTopology().getOutputProperties();
        if (outputProperties != null) {
            Set<String> outputPropertiesOfNode = outputProperties.get(nodeName);
            if (outputPropertiesOfNode != null) {
                Assert.assertFalse(outputPropertiesOfNode.contains(propertyName));
            }
        }
    }

    @When("^I define the attribute \"([^\"]*)\" of the node \"([^\"]*)\" as output attribute$")
    public void I_define_the_attribute_of_the_node_as_output_attribute(String attributeName, String nodeName) throws Throwable {
        Context.getInstance().registerRestResponse(
                Context.getRestClientInstance().postUrlEncoded(getAttributesUrl(attributeName, nodeName, "output"), new ArrayList<NameValuePair>()));
    }

    @Then("^The topology should have the attribute \"([^\"]*)\" of the node \"([^\"]*)\" defined as output attribute$")
    public void The_topology_should_have_the_attribute_of_the_node_defined_as_output_attribute(String attributeName, String nodeName) throws Throwable {
        TopologyDTO topologyDTO = JsonUtil.read(Context.getRestClientInstance().get("/rest/topologies/" + Context.getInstance().getTopologyId()),
                TopologyDTO.class).getData();
        Map<String, Set<String>> outputAttributes = topologyDTO.getTopology().getOutputAttributes();
        Assert.assertNotNull(outputAttributes);
        Set<String> outputAttributesOfNode = outputAttributes.get(nodeName);
        Assert.assertNotNull(outputAttributesOfNode);
        Assert.assertTrue(outputAttributesOfNode.contains(attributeName));
    }

    @When("^I remove the attribute \"([^\"]*)\" of the node \"([^\"]*)\" from the output attributes$")
    public void I_remove_the_attribute_of_the_node_from_the_output_attributes(String attributeName, String nodeName) throws Throwable {
        Context.getInstance().registerRestResponse(Context.getRestClientInstance().delete(getAttributesUrl(attributeName, nodeName, "output")));
    }

    @Then("^The topology should not have the attribute \"([^\"]*)\" of the node \"([^\"]*)\" defined as output attribute$")
    public void The_topology_should_not_have_the_attribute_of_the_node_defined_as_output_attribute(String attributeName, String nodeName) throws Throwable {
        TopologyDTO topologyDTO = JsonUtil.read(Context.getRestClientInstance().get("/rest/topologies/" + Context.getInstance().getTopologyId()),
                TopologyDTO.class).getData();
        Map<String, Set<String>> outputAttributes = topologyDTO.getTopology().getOutputAttributes();
        if (outputAttributes != null) {
            Set<String> outputAttributesOfNode = outputAttributes.get(nodeName);
            if (outputAttributesOfNode != null) {
                Assert.assertFalse(outputAttributesOfNode.contains(attributeName));
            }
        }
    }
}
