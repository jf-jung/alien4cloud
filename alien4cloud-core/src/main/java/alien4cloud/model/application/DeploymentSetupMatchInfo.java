package alien4cloud.model.application;

import lombok.Getter;
import lombok.Setter;
import alien4cloud.cloud.CloudResourceTopologyMatchResult;

@Getter
@Setter
public class DeploymentSetupMatchInfo extends DeploymentSetup {
    private boolean isValid;
    private CloudResourceTopologyMatchResult matchResult;

    /** Default constructor for de-serialization */
    public DeploymentSetupMatchInfo() {
    }

    /**
     * Initialize a {@link DeploymentSetupMatchInfo} from an existing {@link DeploymentSetup};
     * 
     * @param initFrom The deployment setup from which to init the {@link DeploymentSetupMatchInfo}.
     */
    public DeploymentSetupMatchInfo(DeploymentSetup initFrom) {
        super(initFrom.getId(), initFrom.getVersionId(), initFrom.getEnvironmentId(), initFrom.getProviderDeploymentProperties(),
                initFrom.getInputProperties(), initFrom.getCloudResourcesMapping(), initFrom.getNetworkMapping(), initFrom.getStorageMapping());
    }
}